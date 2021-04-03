package ru.moore.lesson7.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.moore.lesson7.model.Product;
import ru.moore.lesson7.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product saveNewProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> findProductByTitle(String title) {
        return productRepository.findAllByTitleContainingIgnoreCase(title);
    }

    public Product findMinСost() {
        return productRepository.findFirstByOrderByCostAsc();
    }

    public Product findMaxCost() {
        return productRepository.findFirstByOrderByCostDesc();
    }

    public List<Product> findCostBetween(Integer min, Integer max) {
        return productRepository.findAllByCostBetween(min, max);
    }

}
