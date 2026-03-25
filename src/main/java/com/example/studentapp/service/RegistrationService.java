package com.example.studentapp.service;

import com.example.studentapp.exception.ResourceNotFoundException;
import com.example.studentapp.model.Course;
import com.example.studentapp.model.Registration;
import com.example.studentapp.model.Student;
import com.example.studentapp.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    // Constructor Injection
    public RegistrationService(RegistrationRepository registrationRepository,
                                StudentService studentService,
                                CourseService courseService) {
        this.registrationRepository = registrationRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    /**
     * Get all registrations
     */
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    /**
     * Get registrations by student ID
     */
    public List<Registration> getRegistrationsByStudentId(Long studentId) {
        return registrationRepository.findByStudentId(studentId);
    }

    /**
     * Register a student for a course
     */
    public Registration registerStudentForCourse(Long studentId, Long courseId, String semester) {
        Student student = studentService.getStudentById(studentId);
        Course course = courseService.getCourseById(courseId);

        // Check for duplicate registration
        if (registrationRepository.existsByStudentAndCourseAndSemester(student, course, semester)) {
            throw new IllegalArgumentException(
                student.getName() + " is already registered for " + course.getCourseName() + " in " + semester);
        }

        Registration registration = new Registration(student, course, semester);
        return registrationRepository.save(registration);
    }

    /**
     * Drop a course (delete registration by ID)
     */
    public void dropRegistration(Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found with ID: " + registrationId));
        registrationRepository.delete(registration);
    }

    /**
     * Count total registrations
     */
    public long countRegistrations() {
        return registrationRepository.count();
    }
}
