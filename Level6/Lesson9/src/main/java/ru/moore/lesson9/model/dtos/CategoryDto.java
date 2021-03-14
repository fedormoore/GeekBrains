package ru.moore.lesson9.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.lesson9.model.entities.Category;
import ru.moore.lesson9.model.entities.Product;

@Data
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;

    public CategoryDto(Category p) {
        this.id = p.getId();
        this.name = p.getName();
    }
}
