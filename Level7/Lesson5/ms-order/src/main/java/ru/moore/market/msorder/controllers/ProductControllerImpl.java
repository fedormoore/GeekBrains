package ru.moore.market.msorder.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductControllerImpl {

    private final ProductController productController;

    public ProductControllerImpl(ProductController productController) {
        this.productController = productController;
    }

    @RequestMapping("/get-product")
    public String greeting() {
        System.out.println(productController.getClass().getName());
        String answer = productController.greeting();
        return answer;
    }
}
