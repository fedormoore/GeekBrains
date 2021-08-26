package ru.moore.lesson7.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.moore.lesson7.models.Student;
import ru.moore.lesson7.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Student student) {
        studentService.saveOrUpdate(student);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        studentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestBody Student student) {
        studentService.saveOrUpdate(student);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Student> findAll() {
        return studentService.findAll();
    }
}
