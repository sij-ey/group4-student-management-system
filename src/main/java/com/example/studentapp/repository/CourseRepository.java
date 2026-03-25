package com.example.studentapp.repository;

import com.example.studentapp.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Find course by code (for duplicate check)
    Optional<Course> findByCourseCode(String courseCode);

    // Check if course code already exists (excluding a given id — for update)
    boolean existsByCourseCodeAndIdNot(String courseCode, Long id);
}
