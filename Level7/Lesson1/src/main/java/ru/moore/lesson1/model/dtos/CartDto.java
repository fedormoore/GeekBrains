package ru.moore.lesson1.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDto {

    private Long id;
    private UserDto user;
    private ProductDto product;

}
