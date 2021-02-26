package ru.moore.lesson4.repositories;

import ru.moore.lesson4.models.Product;

public interface ProductRepository extends CrudRepository<Product> {

    int findMaxId();

}
