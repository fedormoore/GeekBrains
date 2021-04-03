package ru.moore.lesson6.repositories.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.moore.lesson6.models.Customer;
import ru.moore.lesson6.repositories.OrdersRepository;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class OrdersRepositoryImpl implements OrdersRepository {

    private SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public OrdersRepositoryImpl(EntityManagerFactory factory) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    @Override
    public void save(Customer object) {

    }

    @Override
    public void update(Customer object) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Customer> find(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }
}
