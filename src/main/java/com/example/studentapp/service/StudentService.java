package com.example.studentapp.service;

import com.example.studentapp.exception.ResourceNotFoundException;
import com.example.studentapp.model.Student;
import com.example.studentapp.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor Injection (best practice)
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Retrieve all students
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Get student by ID — throws if not found
     */
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
    }

    /**
     * Save a new student
     */
    public Student saveStudent(Student student) {
        // Check for duplicate email
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new IllegalArgumentException("A student with email '" + student.getEmail() + "' already exists.");
        }
        return studentRepository.save(student);
    }

    /**
     * Update existing student
     */
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = getStudentById(id);

        // Ensure email uniqueness excluding this student
        if (studentRepository.existsByEmailAndIdNot(updatedStudent.getEmail(), id)) {
            throw new IllegalArgumentException("Email '" + updatedStudent.getEmail() + "' is already in use.");
        }

        existing.setName(updatedStudent.getName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setProgram(updatedStudent.getProgram());
        existing.setYearOfStudy(updatedStudent.getYearOfStudy());

        return studentRepository.save(existing);
    }

    /**
     * Delete student by ID
     */
    public void deleteStudent(Long id) {
        Student student = getStudentById(id); // ensures it exists
        studentRepository.delete(student);
    }

    /**
     * Count total students
     */
    public long countStudents() {
        return studentRepository.count();
    }
}
