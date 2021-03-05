package ru.moore.lesson6.repositories.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.moore.lesson6.models.Customer;
import ru.moore.lesson6.models.Product;
import ru.moore.lesson6.repositories.ProductRepository;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    private SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public ProductRepositoryImpl(EntityManagerFactory factory) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    @Override
    public void save(Product object) {

    }

    @Override
    public void update(Product object) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Product> find(Long id) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Product product = session.get(Product.class, id);
        session.getTransaction().commit();
        if (product != null) {
            return Optional.of(product);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        return null;
    }


}
