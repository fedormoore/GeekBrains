package ru.moore.market.msorder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moore.market.msorder.model.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
