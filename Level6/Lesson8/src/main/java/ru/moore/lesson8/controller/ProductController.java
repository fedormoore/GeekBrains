package ru.moore.lesson8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.moore.lesson8.exeptions.ThereIsNoValuesInResponse;
import ru.moore.lesson8.model.Product;
import ru.moore.lesson8.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/app/products")
public class ProductController {

    @Autowired
    ProductService productService;

    //http://localhost:8080/app/products/1
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id).orElseThrow(() -> new ThereIsNoValuesInResponse("Продукт с id: " + id + " не существует"));
    }

    @GetMapping
    public List<Product> getAllProduct(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return productService.getAllProduct(page-1, size);
    }

    @GetMapping("/sort_cost_up")
    public List<Product> sortedUp() {
        return productService.sortCostUp();
    }

    @GetMapping("/sort_cost_down")
    public List<Product> sortedDown() {
        return productService.sortCostDown();
    }

    @GetMapping("/sort_title_cost")
    public List<Product> findAllDoubleSort(@RequestParam(defaultValue = "0") int sortTitle, @RequestParam(defaultValue = "0") int sortCost) {
        return productService.sortByTitleAndCost(sortTitle, sortCost);
    }

    @PostMapping
    public Product add(@RequestParam Product product) {
        return productService.saveNewProduct(product);
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    //http://localhost:8080/app/products/title?title=Сыр
    @GetMapping("/title")
    public List<Product> findProductByTitle(@RequestParam String title) {
        return productService.findProductByTitle(title);
    }

    @GetMapping("/min")
    public Product findMinCost() {
        return productService.findMinСost();
    }

    @GetMapping("/max")
    public Product findMaxCost() {
        return productService.findMaxCost();
    }

    //http://localhost:8080/app/products/between/20/50
    @GetMapping("/between/{min}/{max}")
    public List<Product> findCostBetween(@PathVariable Integer min, @PathVariable Integer max) {
        return productService.findCostBetween(min, max);
    }

}
