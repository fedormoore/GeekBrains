package ru.moore.lesson10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
=======
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
>>>>>>> Level6Lesson10
import org.springframework.stereotype.Repository;
import ru.moore.lesson10.model.dtos.ProductDto;
import ru.moore.lesson10.model.entities.Product;

import java.util.List;

@Repository
<<<<<<< HEAD
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<ProductDto> findAllByTitleContainingIgnoreCase(String title);

    ProductDto findFirstByOrderByCostAsc();

    ProductDto findFirstByOrderByCostDesc();

    List<ProductDto> findAllByCostBetween(Integer min, Integer max);

    List<ProductDto> findByOrderByCostAsc();

    List<ProductDto> findByOrderByCostDesc();
=======
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

>>>>>>> Level6Lesson10
}
