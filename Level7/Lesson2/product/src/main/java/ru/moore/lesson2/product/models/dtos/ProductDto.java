package ru.moore.lesson2.product.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String title;
    private Integer cost;
    //private CategoryDto category;

}
