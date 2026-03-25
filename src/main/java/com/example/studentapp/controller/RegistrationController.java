package com.example.studentapp.controller;

import com.example.studentapp.service.CourseService;
import com.example.studentapp.service.RegistrationService;
import com.example.studentapp.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final StudentService studentService;
    private final CourseService courseService;

    public RegistrationController(RegistrationService registrationService,
                                   StudentService studentService,
                                   CourseService courseService) {
        this.registrationService = registrationService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    // ---- LIST ALL REGISTRATIONS ----
    @GetMapping
    public String listAllRegistrations(Model model) {
        model.addAttribute("registrations", registrationService.getAllRegistrations());
        return "registrations/list";
    }

    // ---- SHOW REGISTRATION FORM (for a specific student) ----
    @GetMapping("/new/{studentId}")
    public String showRegisterForm(@PathVariable Long studentId, Model model) {
        model.addAttribute("student", studentService.getStudentById(studentId));
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("semesters", new String[]{"Semester 1 - 2024", "Semester 2 - 2024", "Semester 1 - 2025", "Semester 2 - 2025"});
        return "registrations/form";
    }

    // ---- REGISTER STUDENT FOR COURSE ----
    @PostMapping("/register")
    public String registerStudent(@RequestParam Long studentId,
                                   @RequestParam Long courseId,
                                   @RequestParam String semester,
                                   RedirectAttributes redirectAttributes) {
        try {
            registrationService.registerStudentForCourse(studentId, courseId, semester);
            redirectAttributes.addFlashAttribute("successMessage", "Course registered successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/students/profile/" + studentId;
    }

    // ---- DROP COURSE (delete a registration) ----
    @GetMapping("/drop/{registrationId}")
    public String dropCourse(@PathVariable Long registrationId,
                              @RequestParam Long studentId,
                              RedirectAttributes redirectAttributes) {
        registrationService.dropRegistration(registrationId);
        redirectAttributes.addFlashAttribute("successMessage", "Course dropped successfully.");
        return "redirect:/students/profile/" + studentId;
    }
}
