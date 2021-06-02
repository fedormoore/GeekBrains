package ru.moore.lesson2.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.lesson2.order.models.entities.Cart;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUser_Id(Long id);

}
