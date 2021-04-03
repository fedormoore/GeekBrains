package ru.moore.lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.lesson7.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByTitleContainingIgnoreCase(String title);

    Product findFirstByOrderByCostAsc();

    Product findFirstByOrderByCostDesc();

    List<Product> findAllByCostBetween(Integer min, Integer max);
}
