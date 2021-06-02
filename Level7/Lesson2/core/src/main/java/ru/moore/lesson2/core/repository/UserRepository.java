package ru.moore.lesson2.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moore.lesson2.core.models.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);

}
