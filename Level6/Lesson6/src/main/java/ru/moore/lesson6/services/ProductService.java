package ru.moore.lesson6.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.moore.lesson6.models.Customer;
import ru.moore.lesson6.models.Orders;
import ru.moore.lesson6.models.Product;
import ru.moore.lesson6.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void getListCustomerProduct(Long id) {
        Product product = productRepository.find(id).get();
        System.out.println(product.toString());
        for (Orders order : product.getOrder()) {
            System.out.println("Заказ от " + order.getDate());
            System.out.println("Покупатель " + order.getCustomer().toString());
        }
    }
}
