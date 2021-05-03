package ru.moore.lesson3.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.lesson3.product.models.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
