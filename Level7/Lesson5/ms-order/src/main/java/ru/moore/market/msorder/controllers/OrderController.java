package ru.moore.market.msorder.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.moore.market.msorder.model.dto.OrderDto;
import ru.moore.market.msorder.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveStorage(@RequestBody OrderDto order) {
        orderService.saveOrder(order);
        return "OK";
    }

    @GetMapping("/get")
    public List<OrderDto> getStorage() {
        return orderService.getOrder();
    }
}
