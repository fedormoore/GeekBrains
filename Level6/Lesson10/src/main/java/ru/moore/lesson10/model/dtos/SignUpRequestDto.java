package ru.moore.lesson10.model.dtos;

import lombok.Data;

@Data
public class SignUpRequestDto {

    private String login;

    private String password;
}
