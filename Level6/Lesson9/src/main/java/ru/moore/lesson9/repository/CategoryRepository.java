package ru.moore.lesson9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.lesson9.model.dtos.ProductDto;
import ru.moore.lesson9.model.entities.Category;
import ru.moore.lesson9.model.entities.Product;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
