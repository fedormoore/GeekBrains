package ru.moore.lesson5;

import org.hibernate.cfg.Configuration;
import ru.moore.lesson5.models.Student;
import ru.moore.lesson5.repository.StudentRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;

public class Lesson5Application {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        StudentRepository studentRepository = new StudentRepository(entityManagerFactory);

        for (int i = 0; i < 10; i++) {
            studentRepository.saveOrUpdate(new Student("Student" + i, false));
        }

        List<Student> students = studentRepository.findAll();
        System.out.println("Size: " + students.size());

        for (Student student : students
        ) {
            System.out.println(student);
        }

        Student student = studentRepository.findById(2);
        System.out.println(student);

        System.out.println("Set to Moore");
        student.setName("Moore");
        studentRepository.saveOrUpdate(student);

        studentRepository.delete(1);
    }

}
