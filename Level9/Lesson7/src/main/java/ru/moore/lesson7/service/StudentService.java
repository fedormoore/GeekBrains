package ru.moore.lesson7.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.moore.lesson7.models.Student;
import ru.moore.lesson7.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> findAll() {
        return repository.findAll().stream().map(Student::new).collect(Collectors.toList());
    }

    public boolean deleteById(Long id) {
        if (!repository.findById(id).isEmpty()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public void saveOrUpdate(Student student) {
        Student studentToSave = new Student(student);
        repository.save(studentToSave);
        if (student.getId() == null) {
            student.setId(studentToSave.getId());
        }

    }

}
