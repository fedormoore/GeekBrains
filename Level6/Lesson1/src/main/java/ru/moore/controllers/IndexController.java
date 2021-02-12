package ru.moore.controllers;

import ru.moore.models.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/index")
public class IndexController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Пакет", "10"));
        products.add(new Product(2L, "Пакет", "20"));
        products.add(new Product(3L, "Перец", "30"));
        products.add(new Product(4L, "Соль", "40"));
        products.add(new Product(5L, "Морковь", "50"));
        products.add(new Product(6L, "Картошка", "60"));
        products.add(new Product(7L, "Хлеб", "70"));
        products.add(new Product(8L, "Яйцо", "80"));
        products.add(new Product(9L, "Молоко", "90"));
        products.add(new Product(10L, "Мясо", "100"));


        req.setAttribute("products", products);
        req.getRequestDispatcher("index").forward(req, resp);

    }


}