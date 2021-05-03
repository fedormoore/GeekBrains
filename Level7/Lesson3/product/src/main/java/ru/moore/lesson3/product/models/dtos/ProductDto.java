package ru.moore.lesson3.product.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String title;
    private int cost;
    //private CategoryDto category;

}
