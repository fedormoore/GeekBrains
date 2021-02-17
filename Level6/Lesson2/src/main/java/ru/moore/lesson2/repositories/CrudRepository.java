package ru.moore.lesson2.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    void save(T object);

    void update(T object);

    void delete(int id);

    T find(String title);

    List<T> findAll();
}
