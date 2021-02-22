package ru.moore.lesson4.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    void save(T object);

    void update(T object);

    void delete(int id);

    Optional<T> find(int id);

    List<T> findAll();
}
