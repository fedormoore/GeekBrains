package ru.moore.lesson10.controller;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.moore.lesson10.exeptions.ResourceNotFoundException;
import ru.moore.lesson10.model.dtos.ProductDto;
import ru.moore.lesson10.model.entities.Product;
import ru.moore.lesson10.services.ProductService;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
=======
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.moore.lesson10.exeptions.ResourceNotFoundException;
import ru.moore.lesson10.model.dtos.ProductDto;
import ru.moore.lesson10.repository.specifications.ProductSpecifications;
import ru.moore.lesson10.services.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
>>>>>>> Level6Lesson10
public class ProductController {

    @Autowired
    ProductService productService;

<<<<<<< HEAD
    //Добавить товар в корзину
    //http://localhost:8080/api/v1/products/add_product_in_trash/1
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/add_product_in_trash/{id}")
    public void addProductInTrash(@PathVariable Long id, HttpSession session) {
        if (session != null && id > 0) {
            ProductDto product = productService.getProductById(id).get();
            session.setAttribute("trash", product);
        }
    }

    //Посмотреть товары в корзину
    //http://localhost:8080/api/v1/add_product_in_trash/1
    @GetMapping("/get_trash")
    @ResponseStatus(HttpStatus.CREATED)
    public String addProductInTrash(HttpSession session) {
        if (session != null) {
            return session.getAttribute("trash").toString();
        }
        return null;
    }

    //http://localhost:8080/api/v1/products/1
=======
    //http://localhost:8080/api/v1/products/1
    @PreAuthorize("hasRole('ROLE_USER')")
>>>>>>> Level6Lesson10
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт с id: " + id + " не существует"));
    }

    //http://localhost:8080/api/v1/products?page=50
    @GetMapping
<<<<<<< HEAD
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
=======
    public Page<ProductDto> findAllProduct(@RequestParam MultiValueMap<String, String> params, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size) {
        return productService.findAll(ProductSpecifications.build(params), page, size);
>>>>>>> Level6Lesson10
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
<<<<<<< HEAD
=======
    @PreAuthorize("hasRole('ROLE_ADMIN')")
>>>>>>> Level6Lesson10
    public ProductDto add(@RequestBody ProductDto product) {
        return productService.saveNewProduct(product);
    }

    @GetMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
<<<<<<< HEAD
=======
    @PreAuthorize("hasRole('ROLE_ADMIN')")
>>>>>>> Level6Lesson10
    public void delete(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

<<<<<<< HEAD
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

=======
>>>>>>> Level6Lesson10
}
