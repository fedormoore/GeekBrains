package ru.moore.market.routing.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequestDto {

    private String login;
    private String name;
    private String email;
    private String password;
    private boolean active;
    private String activationCode;

}