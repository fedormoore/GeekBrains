package ru.moore.lesson5.repository;

import java.util.List;

public interface DAOInterface<T> {

    List<T> findAll();

    T findById(long id);

    void saveOrUpdate(T t);

    void delete(long id);
}
