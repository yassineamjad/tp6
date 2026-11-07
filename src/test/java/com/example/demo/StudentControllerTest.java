package com.example.demo;

import com.example.demo.dto.StudentRequest;
import com.example.demo.dto.StudentsByYear;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void saveStudentReturnsCreatedStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setNom("Mido");
        when(studentService.save(any(Student.class))).thenReturn(student);

        ResponseEntity<Student> response = studentController.save(
                new StudentRequest("Mido", "Ali", LocalDate.of(2000, 1, 1)));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Mido", response.getBody().getNom());
    }

    @Test
    void deleteStudentReturnsNoContentWhenDeleted() {
        when(studentService.delete(1L)).thenReturn(true);

        ResponseEntity<Void> response = studentController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteStudentReturnsNotFoundWhenMissing() {
        when(studentService.delete(1L)).thenReturn(false);

        ResponseEntity<Void> response = studentController.delete(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findAllStudentsReturnsAllStudents() {
        when(studentService.findAll()).thenReturn(List.of(new Student(), new Student()));

        ResponseEntity<List<Student>> response = studentController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void countStudentsReturnsStudentCount() {
        when(studentService.countStudents()).thenReturn(10L);

        ResponseEntity<Long> response = studentController.countStudent();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10L, response.getBody());
    }

    @Test
    void findByYearReturnsStudentsGroupedByYear() {
        when(studentService.findStudentsByYear()).thenReturn(List.of(new StudentsByYear(2000, 3)));

        ResponseEntity<List<StudentsByYear>> response = studentController.findByYear();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().get(0).count());
    }
}
