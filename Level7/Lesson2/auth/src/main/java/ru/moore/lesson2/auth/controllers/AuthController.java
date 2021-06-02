package ru.moore.lesson2.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.moore.lesson2.core.configurations.jwt.JwtProvider;
import ru.moore.lesson2.core.models.dtos.AuthRequestDto;
import ru.moore.lesson2.core.models.dtos.AuthResponseDto;
import ru.moore.lesson2.core.models.dtos.SignUpRequestDto;
import ru.moore.lesson2.core.models.entities.User;
import ru.moore.lesson2.core.services.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    //http://localhost:8080/api/v1/user/register
    @PostMapping("/register")
    public String registerUser(@RequestBody SignUpRequestDto signUpRequest) {
        User user = new User();
        user.setPassword(signUpRequest.getPassword());
        user.setLogin(signUpRequest.getLogin());
        userService.saveUser(user);
        return "OK";
    }

    @PostMapping("/auth")
    public AuthResponseDto auth(@RequestBody AuthRequestDto request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponseDto(token);
    }
}
