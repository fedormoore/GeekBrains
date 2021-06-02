package ru.moore.lesson4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.moore.lesson4.exceptions.ResourceNotFoundException;
import ru.moore.lesson4.models.Product;
import ru.moore.lesson4.services.ProductService;

import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("total", productService.getTotalProduct());
        model.addAttribute("average", productService.getAverageCost());
        model.addAttribute("products", productService.findAllProduct());
        return "index";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProductById(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }

    @PostMapping("/add")
    public String addNewProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/";
    }

    @GetMapping("/get")
    public String getProduct(Model model, @RequestParam int id) {
        Optional<Product> product = productService.getProduct(id);

        if (!product.isPresent()) throw new ResourceNotFoundException("Нет продутка с id " + id);

        model.addAttribute("product", product.get());

        return "getProduct";
    }

}
