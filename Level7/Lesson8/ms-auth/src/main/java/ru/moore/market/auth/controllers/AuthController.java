package ru.moore.market.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.moore.market.auth.services.UserService;
import ru.moore.market.core.exceptions.ResourceNotFoundException;
import ru.moore.market.routing.dtos.AuthRequestDto;
import ru.moore.market.routing.dtos.SignUpRequestDto;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping
    public String activate() {
        return "new RedirectView()";
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequestDto signUpRequest) {
        return userService.signUp(signUpRequest);
    }

    @GetMapping("/activate/{code}")
    public RedirectView activate(@PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        return new RedirectView("http://localhost:3000/login");
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequestDto authRequest) throws ResourceNotFoundException {
        return userService.findByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword());
    }

}