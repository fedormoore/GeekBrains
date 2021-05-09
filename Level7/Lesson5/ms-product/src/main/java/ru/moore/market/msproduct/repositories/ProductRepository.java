package ru.moore.market.msproduct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moore.market.msproduct.model.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
