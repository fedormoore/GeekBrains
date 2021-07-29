package ru.moore.msauth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moore.msauth.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);

}