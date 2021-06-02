package ru.moore.lesson3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.moore.lesson3.models.Product;
import ru.moore.lesson3.repositories.ProductRepository;
import ru.moore.lesson3.services.ProductService;

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

    @GetMapping("/delete/{id}")
    public String deleteProductById(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }

    @PostMapping("/add")
    public String addNewProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/";
    }

}
