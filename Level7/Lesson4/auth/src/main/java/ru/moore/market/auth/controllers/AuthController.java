package ru.moore.market.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.moore.market.auth.models.entities.User;
import ru.moore.market.auth.services.UserService;
import ru.moore.market.core.interfaces.ITokenService;
import ru.moore.market.core.models.dtos.AuthRequestDto;
import ru.moore.market.core.models.dtos.AuthResponseDto;
import ru.moore.market.core.models.dtos.SignUpRequestDto;
import ru.moore.market.core.models.entities.UserInfo;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private ITokenService tokenService;

    //http://localhost:8080/api/v1/user/signup
    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpRequestDto signUpRequest) {
        User user = new User();
        user.setPassword(signUpRequest.getPassword());
        user.setLogin(signUpRequest.getLogin());
        userService.saveUser(user);
        return "OK";
    }

    //http://localhost:8080/api/v1/user/login
    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());

        UserInfo userInfo = UserInfo.builder()
                .userId(user.getId())
                .login(user.getLogin())
                .role(user.getRole().getName())
                .build();
        String token = tokenService.generateToken(userInfo);
        return new AuthResponseDto(token);
    }

    //http://localhost:8080/api/v1/user/logout
    @PostMapping("/logout")
    public AuthResponseDto logout(@RequestBody AuthRequestDto request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());

        UserInfo userInfo = UserInfo.builder()
                .userId(user.getId())
                .login(user.getLogin())
                .role(user.getRole().getName())
                .build();
        String token = tokenService.generateToken(userInfo);
        return new AuthResponseDto(token);
    }
}
