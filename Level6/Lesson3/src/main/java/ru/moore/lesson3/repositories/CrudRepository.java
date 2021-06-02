package ru.moore.lesson3.repositories;

import java.util.List;

public interface CrudRepository<T> {

    void save(T object);

    void update(T object);

    void delete(int id);

    T find(String title);

    List<T> findAll();
}
