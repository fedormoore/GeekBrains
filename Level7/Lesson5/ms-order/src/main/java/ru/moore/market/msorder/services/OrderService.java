package ru.moore.market.msorder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.moore.market.msorder.model.dto.OrderDto;
import ru.moore.market.msorder.model.entities.Order;
import ru.moore.market.msorder.repositories.OrderRepository;
import ru.moore.market.msorder.services.mappers.MapperUtils;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    MapperUtils mapperUtils;

    @Autowired
    OrderRepository orderRepository;

    public void saveOrder(OrderDto order) {
        orderRepository.save(mapperUtils.map(order, Order.class));
    }

    public List<OrderDto> getOrder() {
        return mapperUtils.mapAll(orderRepository.findAll(), OrderDto.class);
    }

}
