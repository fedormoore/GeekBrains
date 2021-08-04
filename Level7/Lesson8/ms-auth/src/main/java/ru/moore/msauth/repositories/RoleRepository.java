package ru.moore.msauth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moore.msauth.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}