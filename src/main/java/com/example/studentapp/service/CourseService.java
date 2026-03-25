package com.example.studentapp.service;

import com.example.studentapp.exception.ResourceNotFoundException;
import com.example.studentapp.model.Course;
import com.example.studentapp.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    // Constructor Injection
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Retrieve all courses
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Get course by ID
     */
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));
    }

    /**
     * Save a new course
     */
    public Course saveCourse(Course course) {
        if (courseRepository.findByCourseCode(course.getCourseCode()).isPresent()) {
            throw new IllegalArgumentException("Course with code '" + course.getCourseCode() + "' already exists.");
        }
        return courseRepository.save(course);
    }

    /**
     * Update existing course
     */
    public Course updateCourse(Long id, Course updatedCourse) {
        Course existing = getCourseById(id);

        if (courseRepository.existsByCourseCodeAndIdNot(updatedCourse.getCourseCode(), id)) {
            throw new IllegalArgumentException("Course code '" + updatedCourse.getCourseCode() + "' is already in use.");
        }

        existing.setCourseName(updatedCourse.getCourseName());
        existing.setCourseCode(updatedCourse.getCourseCode());
        existing.setLecturer(updatedCourse.getLecturer());
        existing.setCredits(updatedCourse.getCredits());

        return courseRepository.save(existing);
    }

    /**
     * Delete a course
     */
    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }

    /**
     * Count total courses
     */
    public long countCourses() {
        return courseRepository.count();
    }
}
