package ru.moore.market.msproduct.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.moore.market.msproduct.model.dto.ProductDto;
import ru.moore.market.msproduct.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@EnableCircuitBreaker
public class ProductController {

    @Autowired
    private ProductService storageService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveStorage(@RequestBody ProductDto storage) {
        storageService.saveStorage(storage);
        return "OK";
    }

    @GetMapping("/finAll")
    @HystrixCommand(fallbackMethod = "demoFallback")
    public List<ProductDto> finAll() {
        return storageService.finAll();
    }
}
