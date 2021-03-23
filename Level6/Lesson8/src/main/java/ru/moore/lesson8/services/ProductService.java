package ru.moore.lesson8.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.moore.lesson8.exeptions.ThereIsNoValuesInResponse;
import ru.moore.lesson8.model.Product;
import ru.moore.lesson8.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProduct(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new ThereIsNoValuesInResponse("Неверный запрос");
        }
        return checkPagination(page, size, productRepository.findAll(PageRequest.of(page, size)));
    }

    private List<Product> checkPagination(int page, int size, Page<Product> result) {
        if (!result.hasContent()) {
            throw new ThereIsNoValuesInResponse("Страница не найдена");
        }
        return result.toList();
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

    public List<Product> sortCostUp() {
        return productRepository.findByOrderByCostAsc();
    }

    public List<Product> sortCostDown() {
        return productRepository.findByOrderByCostDesc();
    }

    public List<Product> sortByTitleAndCost(int sortTitle, int sortCost) {
        if (sortTitle == 0 & sortCost == 0) {
            return productRepository.findAll(Sort.by("title").descending().and(Sort.by("cost").descending()));
        }
        if (sortTitle == 1 & sortCost == 0) {
            return productRepository.findAll(Sort.by("title").ascending().and(Sort.by("cost").descending()));
        }
        if (sortTitle == 0 & sortCost == 1) {
            return productRepository.findAll(Sort.by("title").descending().and(Sort.by("cost").ascending()));
        } else {
            return productRepository.findAll(Sort.by("title").ascending().and(Sort.by("cost").ascending()));
        }
    }
}
