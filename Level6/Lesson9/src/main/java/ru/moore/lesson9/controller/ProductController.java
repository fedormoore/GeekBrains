package ru.moore.lesson9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.moore.lesson9.exeptions.ResourceNotFoundException;
import ru.moore.lesson9.model.dtos.ProductDto;
import ru.moore.lesson9.model.entities.Product;
import ru.moore.lesson9.services.ProductService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    //http://localhost:8080/api/v1/products/1
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт с id: " + id + " не существует"));
    }

    //http://localhost:8080/api/v1/products?page=50
    @GetMapping
    public List<ProductDto> getAllProduct(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return productService.getAllProduct(page, size);
    }

    @GetMapping("/sort_cost_up")
    public List<ProductDto> sortedUp() {
        return productService.sortCostUp();
    }

    @GetMapping("/sort_cost_down")
    public List<ProductDto> sortedDown() {
        return productService.sortCostDown();
    }

    //http://localhost:8080/api/v1/products/sort_title_cost?sortCost=1
    @GetMapping("/sort_title_cost")
    public List<ProductDto> findAllDoubleSort(@RequestParam(defaultValue = "0") int sortTitle, @RequestParam(defaultValue = "0") int sortCost) {
        return productService.sortByTitleAndCost(sortTitle, sortCost);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto add(@RequestBody ProductDto product) {
        return productService.saveNewProduct(product);
    }

    @GetMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    //http://localhost:8080/app/products/title?title=Сыр
    @GetMapping("/title")
    public List<ProductDto> findProductByTitle(@RequestParam String title) {
        return productService.findProductByTitle(title);
    }

    @GetMapping("/min")
    public ProductDto findMinCost() {
        return productService.findMinСost();
    }

    @GetMapping("/max")
    public ProductDto findMaxCost() {
        return productService.findMaxCost();
    }

    //http://localhost:8080/app/products/between/20/50
    @GetMapping("/between/{min}/{max}")
    public List<ProductDto> findCostBetween(@PathVariable Integer min, @PathVariable Integer max) {
        return productService.findCostBetween(min, max);
    }

}
