package com.example.studentapp.repository;

import com.example.studentapp.model.Registration;
import com.example.studentapp.model.Student;
import com.example.studentapp.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    // Get all registrations for a student
    List<Registration> findByStudent(Student student);

    // Get all registrations for a specific student id
    List<Registration> findByStudentId(Long studentId);

    // Get all registrations for a specific course id
    List<Registration> findByCourseId(Long courseId);

    // Check if a student is already registered for a course in a semester
    boolean existsByStudentAndCourseAndSemester(Student student, Course course, String semester);

    // Find a specific registration
    Optional<Registration> findByStudentIdAndCourseId(Long studentId, Long courseId);
}
