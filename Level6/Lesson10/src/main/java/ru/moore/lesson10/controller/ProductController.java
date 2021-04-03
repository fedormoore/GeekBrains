package ru.moore.lesson10.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.moore.lesson10.exeptions.ResourceNotFoundException;
import ru.moore.lesson10.model.dtos.ProductDto;
import ru.moore.lesson10.repository.specifications.ProductSpecifications;
import ru.moore.lesson10.services.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    ProductService productService;

    //http://localhost:8080/api/v1/products/1
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт с id: " + id + " не существует"));
    }

    //http://localhost:8080/api/v1/products?page=50
    @GetMapping
    public Page<ProductDto> findAllProduct(@RequestParam MultiValueMap<String, String> params, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size) {
        return productService.findAll(ProductSpecifications.build(params), page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductDto add(@RequestBody ProductDto product) {
        return productService.saveNewProduct(product);
    }

    @GetMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

}
