package ru.moore.lesson4.repositories;

import java.util.List;

public interface CrudRepository<T> {

    void save(T object);

    void update(T object);

    void delete(int id);

    T find(String title);

    List<T> findAll();
}
