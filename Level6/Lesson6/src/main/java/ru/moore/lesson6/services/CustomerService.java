package ru.moore.lesson6.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.moore.lesson6.models.Customer;
import ru.moore.lesson6.models.Orders;
import ru.moore.lesson6.models.Product;
import ru.moore.lesson6.repositories.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void getCustomerById(Long id) {
        Customer customer = customerRepository.find(id).get();
        System.out.println(customer.toString());
        for (Orders order : customer.getOrders()) {
            System.out.println("Заказ от " + order.getDate());
            for (Product product : order.getProducts()) {
                System.out.println(product.toString());
            }
        }
    }
}
