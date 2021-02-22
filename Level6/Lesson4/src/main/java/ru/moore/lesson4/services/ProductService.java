package ru.moore.lesson4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.moore.lesson4.models.Product;
import ru.moore.lesson4.repositories.ProductRepository;

import java.util.List;

@Component
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String getTotalProduct() {
        return "Количество продуктов: " + productRepository.findAll().size();
    }

    public String getAverageCost() {
        List<Product> products = productRepository.findAll();
        if (products.size() == 0) {
            return "Средняя стоимость товара сосовляет: 0";
        }

        int avg = 0;
        for (Product product : products) {
            avg += product.getCost();
        }
        avg /= products.size();
        return "Средняя стоимость товара сосовляет: " + avg;
    }

    public void addProduct(Product product) {
        int id = productRepository.findMaxId() + 1;
        Product newProduct = new Product(id, product.getTitle(), product.getCost());
        productRepository.save(newProduct);
    }

    public void deleteProduct(int id) {
        productRepository.delete(id);
    }

    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }
}
