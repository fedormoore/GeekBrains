package ru.moore.lesson9.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.lesson9.model.dtos.ProductDto;
import ru.moore.lesson9.model.entities.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<ProductDto> findAllByTitleContainingIgnoreCase(String title);

    ProductDto findFirstByOrderByCostAsc();

    ProductDto findFirstByOrderByCostDesc();

    List<ProductDto> findAllByCostBetween(Integer min, Integer max);

    List<ProductDto> findByOrderByCostAsc();

    List<ProductDto> findByOrderByCostDesc();
}
