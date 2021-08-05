package ru.moore.routinglib.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.moore.routinglib.dtos.ProductDto;

import java.util.List;

@FeignClient("ms-product")
public interface ProductClient {

    @GetMapping("/api/v1/products/{id}")
    ProductDto findProductById(@PathVariable Long id);

    @GetMapping("/api/v1/products/ids")
    List<ProductDto> findProductsByIds(@RequestParam List<Long> ids);

    @PostMapping("/api/v1/products")
    @ResponseStatus(HttpStatus.CREATED)
    ProductDto saveNewProduct(@RequestBody ProductDto product);

    @PutMapping("/api/v1/products")
    ProductDto updateProduct(@RequestBody ProductDto product);

    @DeleteMapping("/api/v1/products/{id}")
    void updateProduct(@PathVariable Long id);
}
