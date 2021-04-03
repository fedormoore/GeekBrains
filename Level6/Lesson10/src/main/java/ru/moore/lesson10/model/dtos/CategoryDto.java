package ru.moore.lesson10.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.lesson10.model.entities.Category;

@Data
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;

}
