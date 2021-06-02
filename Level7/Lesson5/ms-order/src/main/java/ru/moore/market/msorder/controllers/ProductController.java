package ru.moore.market.msorder.controllers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("ms-product")
public interface ProductController {

    @GetMapping("/finAll")
    String greeting();

}
