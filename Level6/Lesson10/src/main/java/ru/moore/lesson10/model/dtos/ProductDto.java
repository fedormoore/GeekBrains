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

    public ProductDto(Product p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.cost = p.getCost();
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

}
