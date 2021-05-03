package ru.moore.market.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moore.market.auth.models.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}