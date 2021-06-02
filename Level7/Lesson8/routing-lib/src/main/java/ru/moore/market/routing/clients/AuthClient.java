package ru.moore.market.routing.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.moore.market.routing.dtos.AuthRequestDto;
import ru.moore.market.routing.dtos.AuthResponseDto;
import ru.moore.market.routing.dtos.SignUpRequestDto;

@FeignClient("ms-auth")
public interface AuthClient {

    @PostMapping("/signUp")
    String signUp(@RequestBody SignUpRequestDto signUpRequest);

    @PostMapping("/signIn")
    AuthResponseDto login(@RequestBody AuthRequestDto request);
}
