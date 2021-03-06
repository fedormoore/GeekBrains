package ru.moore.lesson10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.lesson10.model.dtos.AuthRequestDto;
import ru.moore.lesson10.model.dtos.CartDto;
import ru.moore.lesson10.model.dtos.UserDto;
import ru.moore.lesson10.model.entities.Cart;
import ru.moore.lesson10.model.entities.User;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUser_Id(Long id);

}
