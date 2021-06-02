package ru.moore.lesson2.product.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;

}
