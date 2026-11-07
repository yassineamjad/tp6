package com.example.demo;

import com.example.demo.dto.StudentRequest;
import com.example.demo.dto.StudentsByYear;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> save(@Valid @RequestBody StudentRequest request) {
        Student student = new Student();
        student.setNom(request.nom());
        student.setPrenom(request.prenom());
        student.setDateNaissance(request.dateNaissance());
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return studentService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Student>> findAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countStudent() {
        return ResponseEntity.ok(studentService.countStudents());
    }

    @GetMapping("/by-year")
    public ResponseEntity<List<StudentsByYear>> findByYear() {
        return ResponseEntity.ok(studentService.findStudentsByYear());
    }
}
