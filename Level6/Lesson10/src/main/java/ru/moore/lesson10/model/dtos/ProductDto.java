package ru.moore.lesson10.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.lesson10.model.entities.Product;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String title;
    private Integer cost;
    private CategoryDto category;

}
