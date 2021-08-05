package ru.moore.msorder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import ru.moore.corelib.interfaces.ITokenService;
import ru.moore.corelib.models.UserInfo;
import ru.moore.msorder.services.CartService;
import ru.moore.routinglib.dtos.CartDto;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final ITokenService tokenService;

    @PostMapping
    public UUID createNewCart(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) {
        if (token == null) {
            return cartService.getCartForUser(null, null);
        }
        UserInfo userInfo = tokenService.parseToken(token.replace("Bearer ", ""));
        return cartService.getCartForUser(userInfo.getUserId(), null);
    }

    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@PathVariable UUID uuid) {
        return cartService.findById(uuid);
    }

    @PostMapping("/add")
    public void addProductToCart(@RequestParam UUID uuid, @RequestParam(name = "product_id") Long productId) {
        cartService.addToCart(uuid, productId);
    }

    @PostMapping("/clear")
    public void clearCart(@RequestParam UUID uuid) {
        cartService.clearCart(uuid);
    }
}
