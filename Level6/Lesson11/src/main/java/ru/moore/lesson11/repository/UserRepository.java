package ru.moore.lesson11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moore.lesson11.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String s);

    Optional<User> findById(Long id);
}
