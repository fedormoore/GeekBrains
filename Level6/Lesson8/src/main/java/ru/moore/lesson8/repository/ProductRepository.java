package ru.moore.lesson8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.lesson8.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByTitleContainingIgnoreCase(String title);

    Product findFirstByOrderByCostAsc();

    Product findFirstByOrderByCostDesc();

    List<Product> findAllByCostBetween(Integer min, Integer max);

    List<Product> findByOrderByCostAsc();

    List<Product> findByOrderByCostDesc();
}
