package ru.moore.lesson10.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.lesson10.model.entities.Cart;
import ru.moore.lesson10.model.entities.Product;

@Data
@NoArgsConstructor
public class CartDto {

    private Long id;
    private UserDto user;
    private ProductDto product;

}
