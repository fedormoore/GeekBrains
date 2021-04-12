package ru.moore.lesson10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moore.lesson10.model.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);

}
