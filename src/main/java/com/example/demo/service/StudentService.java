package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.dto.StudentsByYear;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public boolean delete(Long id) {
        if (!studentRepository.existsById(id)) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public long countStudents() {
        return studentRepository.count();
    }

    public List<StudentsByYear> findStudentsByYear() {
        return studentRepository.findNbrStudentByYear().stream()
                .map(row -> new StudentsByYear(((Number) row[0]).intValue(), ((Number) row[1]).longValue()))
                .collect(Collectors.toList());
    }
}