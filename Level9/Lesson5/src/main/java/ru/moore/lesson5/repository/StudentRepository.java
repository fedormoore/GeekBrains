package ru.moore.lesson5.repository;

import ru.moore.lesson5.models.Student;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class StudentRepository extends AbstractRepository implements DAOInterface<Student> {

    public StudentRepository(EntityManagerFactory factory) {
        super(factory);
    }

    @Override
    public List<Student> findAll() {
        return executeForEntityManager(entityManager -> (entityManager.createQuery("from Student s", Student.class).getResultList()));
    }

    @Override
    public Student findById(long id) {
        return executeForEntityManager(entityManager -> (entityManager.find(Student.class, id)));
    }

    @Override
    public void saveOrUpdate(Student student) {
        if (student.getId() != null) {
            update(student);
        } else {
            insert(student);
        }
    }

    private void insert(Student student) {
        executeInTransaction(entityManager -> entityManager.persist(student));
    }

    private void update(Student student) {
        executeInTransaction(entityManager -> entityManager.merge(student));
    }

    @Override
    public void delete(long id) {
        executeInTransaction(entityManager -> {
            Student student = entityManager.find(Student.class, id);
            if (student != null) {
                entityManager.remove(student);
            }
        });
    }

}
