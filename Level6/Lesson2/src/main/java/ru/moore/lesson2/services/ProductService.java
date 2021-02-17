package ru.moore.lesson2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.moore.lesson2.models.Product;
import ru.moore.lesson2.repositories.ProductRepository;

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
            return "0";
        }

        int avg = 0;
        for (Product product : products) {
            avg += product.getCost();
        }
        avg /= products.size();
        return "Средняя стоимость товара сосовляет: " + avg;
    }

    public String addProduct(String cmd) {
        String[] newProduct = cmd.split(":");
        int id = productRepository.findMaxId() + 1;
        Product product = new Product(id, newProduct[0], Integer.valueOf(newProduct[1]));
        productRepository.save(product);
        return "Продукт добавлен";
    }

    public String updateProduct(String cmd) {
        String[] newProduct = cmd.split(":");
        if (newProduct.length < 3) {
            return "Формат не совпадает";
        }
        if(!newProduct[0].matches("[-+]?\\d+") || !newProduct[2].matches("[-+]?\\d+")){
            return "Формат не совпадает";
        }

        Product product = new Product(Integer.valueOf(newProduct[0]), newProduct[1], Integer.valueOf(newProduct[2]));
        productRepository.update(product);
        return "Продукт обнавлен";
    }

    public String deleteProduct(String cmd) {
        productRepository.delete(Integer.valueOf(cmd));
        return "Продукт удален";
    }

    public String findProduct(String cmd) {
        return productRepository.find(cmd).toString();
    }

    public void findAllProduct() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }
}
