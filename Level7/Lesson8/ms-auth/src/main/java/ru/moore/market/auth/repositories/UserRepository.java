package ru.moore.market.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moore.market.auth.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    User findByActivationCode(String code);

    Optional<User> findByLogin(String login);
}