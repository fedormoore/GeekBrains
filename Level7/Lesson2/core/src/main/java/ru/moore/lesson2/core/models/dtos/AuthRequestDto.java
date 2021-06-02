package ru.moore.lesson2.core.models.dtos;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String login;
    private String password;
}
