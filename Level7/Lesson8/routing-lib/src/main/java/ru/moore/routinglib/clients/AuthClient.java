package ru.moore.routinglib.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.moore.routinglib.dtos.AuthRequestDto;
import ru.moore.routinglib.dtos.AuthResponseDto;
import ru.moore.routinglib.dtos.SignUpRequestDto;

@FeignClient("ms-auth")
public interface AuthClient {

    @PostMapping("/signup")
    String signUp(@RequestBody SignUpRequestDto signUpRequest);

    @PostMapping("/login")
    AuthResponseDto login(@RequestBody AuthRequestDto request);
}
