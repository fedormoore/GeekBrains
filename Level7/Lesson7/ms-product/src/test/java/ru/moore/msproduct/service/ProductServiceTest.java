package ru.moore.msproduct.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.moore.msproduct.models.Product;
import ru.moore.msproduct.repositories.ProductRepository;
import ru.moore.msproduct.services.ProductService;
import ru.moore.routinglib.dtos.ProductDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ProductService.class, ModelMapper.class})
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;

    @Test
    public void findProductByTitleTest() {
        Product demoProduct = new Product();
        demoProduct.setTitle("Колбаса");
        demoProduct.setId(3L);
        Mockito.doReturn(demoProduct)
                .when(productRepository)
                .findByTitle("Колбаса");
        Product p = productService.findProductByTitleTest("Колбаса");
        assertEquals(demoProduct, p);
    }

    @Test
    void findProductByID() {
        Product newProduct = new Product();
        newProduct.setTitle("Колбаса");
        newProduct.setId(3L);

        Mockito.doReturn(Optional.of(newProduct))
                .when(productRepository)
                .findById(3L);

        ProductDto testDto = productService.findProductDtoById(3L).get();

        assertEquals(testDto.getTitle(), "Колбаса");
    }
}

