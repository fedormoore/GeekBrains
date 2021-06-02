package ru.moore.market.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moore.market.auth.models.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);

}