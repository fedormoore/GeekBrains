package ru.moore.lesson2.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moore.lesson2.core.models.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}
