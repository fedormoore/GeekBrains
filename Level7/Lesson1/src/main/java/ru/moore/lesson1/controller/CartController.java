package ru.moore.lesson1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.moore.lesson1.model.dtos.CartDto;
import ru.moore.lesson1.services.CartService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/add")
    public String  addProductToCustomerCart(@RequestParam String productName, Principal principal) {
        cartService.addToCart(principal.getName(), productName);
        return "Ok";
    }

    @GetMapping("/show")
    public List<CartDto> showCustomerCarts(Principal principal){
        return cartService.showUserCarts(principal.getName());
    }

}
