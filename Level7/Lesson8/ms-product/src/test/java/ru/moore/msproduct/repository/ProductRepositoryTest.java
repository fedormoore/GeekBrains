package ru.moore.msproduct.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.moore.msproduct.models.Product;
import ru.moore.msproduct.repositories.ProductRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void findProductByTitle() {
       Optional<Product> testProduct =  productRepository.findByTitle("Яйцо");
       assertEquals(testProduct.get().getTitle(),"Яйцо");
       assertEquals(testProduct.get().getId(),2L);
    }

}