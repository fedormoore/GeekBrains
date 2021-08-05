package ru.moore.routinglib.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CartDto {
    private List<CartItemDto> items;
    private int totalPrice;
}
