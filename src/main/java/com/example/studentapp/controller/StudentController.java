package com.example.studentapp.controller;

import com.example.studentapp.model.Student;
import com.example.studentapp.service.RegistrationService;
import com.example.studentapp.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final RegistrationService registrationService;

    public StudentController(StudentService studentService, RegistrationService registrationService) {
        this.studentService = studentService;
        this.registrationService = registrationService;
    }

    // ---- LIST ALL STUDENTS ----
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students/list";
    }

    // ---- SHOW ADD FORM ----
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("formTitle", "Add New Student");
        return "students/form";
    }

    // ---- SAVE NEW STUDENT ----
    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("formTitle", "Add New Student");
            return "students/form";
        }
        try {
            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("successMessage", "Student added successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("formTitle", "Add New Student");
            return "students/form";
        }
        return "redirect:/students";
    }

    // ---- SHOW EDIT FORM ----
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("formTitle", "Edit Student");
        return "students/form";
    }

    // ---- UPDATE STUDENT ----
    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                 @Valid @ModelAttribute("student") Student student,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("formTitle", "Edit Student");
            return "students/form";
        }
        try {
            studentService.updateStudent(id, student);
            redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("formTitle", "Edit Student");
            return "students/form";
        }
        return "redirect:/students";
    }

    // ---- DELETE STUDENT ----
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully.");
        return "redirect:/students";
    }

    // ---- VIEW STUDENT PROFILE (with their courses) ----
    @GetMapping("/profile/{id}")
    public String viewProfile(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("registrations", registrationService.getRegistrationsByStudentId(id));
        return "students/profile";
    }
}
