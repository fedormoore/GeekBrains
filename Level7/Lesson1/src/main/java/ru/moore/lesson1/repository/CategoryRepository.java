package ru.moore.lesson1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.lesson1.model.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
