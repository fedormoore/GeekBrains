package ru.moore.lesson2.repositories;

import ru.moore.lesson2.models.Product;

public interface ProductRepository extends CrudRepository<Product> {

    int findMaxId();

}
