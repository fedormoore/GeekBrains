package ru.moore.lesson10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.lesson10.model.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
