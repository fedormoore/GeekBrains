package ru.moore.lesson1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moore.lesson1.model.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}
