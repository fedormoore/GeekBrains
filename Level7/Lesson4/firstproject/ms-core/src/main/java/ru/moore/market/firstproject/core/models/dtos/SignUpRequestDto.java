package ru.moore.market.firstproject.core.models.dtos;

import lombok.Data;

@Data
public class SignUpRequestDto {

    private String login;

    private String password;

}