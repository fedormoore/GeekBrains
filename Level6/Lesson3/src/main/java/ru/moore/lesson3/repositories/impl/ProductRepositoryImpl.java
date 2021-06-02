package ru.moore.lesson3.repositories.impl;

import org.springframework.stereotype.Component;
import ru.moore.lesson3.models.Product;
import ru.moore.lesson3.repositories.ProductRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    private List<Product> products;

    @PostConstruct
    public void init() {
        products = new ArrayList<>();
        products.add(new Product(1, "Пакет", 10));
        products.add(new Product(2, "Пакет", 20));
        products.add(new Product(3, "Перец", 30));
        products.add(new Product(4, "Соль", 40));
        products.add(new Product(5, "Морковь", 50));
        products.add(new Product(6, "Картошка", 60));
        products.add(new Product(7, "Хлеб", 70));
        products.add(new Product(8, "Яйцо", 80));
        products.add(new Product(9, "Молоко", 90));
        products.add(new Product(10, "Мясо", 100));
    }


    @Override
    public void save(Product object) {
        products.add(object);
    }

    @Override
    public void update(Product object) {
        products.get(object.getId() - 1).setTitle(object.getTitle());
        products.get(object.getId() - 1).setCost(object.getCost());
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.remove(i);
                break;
            }
        }
    }

    @Override
    public Product find(String title) {
        for (Product product : products) {
            if (product.getTitle().equals(title)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public int findMaxId() {
        int max = products.get(0).getId();
        for (int i = 1; i < products.size(); i++) {
            if (max < products.get(i).getId()) {
                max = products.get(i).getId();
            }
        }
        return max;
    }
}
