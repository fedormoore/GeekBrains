package ru.moore.market.firstproject.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.moore.market.firstproject.auth.models.entities.User;
import ru.moore.market.firstproject.auth.services.UserService;
import ru.moore.market.firstproject.core.interfaces.ITokenService;
import ru.moore.market.firstproject.core.models.dtos.AuthRequestDto;
import ru.moore.market.firstproject.core.models.dtos.AuthResponseDto;
import ru.moore.market.firstproject.core.models.dtos.SignUpRequestDto;
import ru.moore.market.firstproject.core.models.entities.SecurityToken;
import ru.moore.market.firstproject.core.models.entities.UserInfo;
import ru.moore.market.firstproject.core.services.JWTTokenService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private JWTTokenService jwtTokenService;

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
    public void logout(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = authorizationHeader.replace("Bearer ", "");
        SecurityToken securityToken = new SecurityToken();
        securityToken.setToken(token);
        UserInfo userInfo = tokenService.parseToken(token);
        Date nowDate= new Date();
        long diffInMillies = Math.abs((userInfo.getTimeToLive().getTime() - nowDate.getTime())/1000);
        securityToken.setTimeToLive(diffInMillies);
        jwtTokenService.addInvalidToken(securityToken);
    }

}
