package com.example.studentapp.repository;

import com.example.studentapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Find student by email (for duplicate check)
    Optional<Student> findByEmail(String email);

    // Check if an email already exists (excluding a given student id — for update)
    boolean existsByEmailAndIdNot(String email, Long id);

    // Find students by program
    java.util.List<Student> findByProgram(String program);
}
