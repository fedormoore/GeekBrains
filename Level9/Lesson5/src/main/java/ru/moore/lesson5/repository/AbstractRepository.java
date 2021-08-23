package ru.moore.lesson5.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Consumer;
import java.util.function.Function;

public class AbstractRepository {

    protected EntityManagerFactory factory;

    public AbstractRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    protected <R> R executeForEntityManager(Function<EntityManager, R> function) {
        EntityManager manager = factory.createEntityManager();
        try {
            return function.apply(manager);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    protected void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            consumer.accept(manager);
            manager.getTransaction().commit();
        } catch (Exception ex) {
            manager.getTransaction().rollback();
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

}
