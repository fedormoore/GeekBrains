package ru.moore.lesson3.repositories;

import ru.moore.lesson3.models.Product;

public interface ProductRepository extends CrudRepository<Product> {

    int findMaxId();

}
