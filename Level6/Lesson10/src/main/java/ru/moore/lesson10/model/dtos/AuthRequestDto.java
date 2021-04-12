package ru.moore.lesson10.model.dtos;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String login;
    private String password;
}
