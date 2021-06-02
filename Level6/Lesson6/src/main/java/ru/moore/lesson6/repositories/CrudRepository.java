package ru.moore.lesson6.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    void save(T object);

    void update(T object);

    void delete(Long id);

    Optional<T> find(Long id);

    List<T> findAll();
}
