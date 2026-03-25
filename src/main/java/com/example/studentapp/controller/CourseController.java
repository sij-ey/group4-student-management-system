package com.example.studentapp.controller;

import com.example.studentapp.model.Course;
import com.example.studentapp.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // ---- LIST ALL COURSES ----
    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses/list";
    }

    // ---- SHOW ADD FORM ----
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("formTitle", "Add New Course");
        return "courses/form";
    }

    // ---- SAVE NEW COURSE ----
    @PostMapping("/save")
    public String saveCourse(@Valid @ModelAttribute("course") Course course,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("formTitle", "Add New Course");
            return "courses/form";
        }
        try {
            courseService.saveCourse(course);
            redirectAttributes.addFlashAttribute("successMessage", "Course added successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("formTitle", "Add New Course");
            return "courses/form";
        }
        return "redirect:/courses";
    }

    // ---- SHOW EDIT FORM ----
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("formTitle", "Edit Course");
        return "courses/form";
    }

    // ---- UPDATE COURSE ----
    @PostMapping("/update/{id}")
    public String updateCourse(@PathVariable Long id,
                                @Valid @ModelAttribute("course") Course course,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("formTitle", "Edit Course");
            return "courses/form";
        }
        try {
            courseService.updateCourse(id, course);
            redirectAttributes.addFlashAttribute("successMessage", "Course updated successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("formTitle", "Edit Course");
            return "courses/form";
        }
        return "redirect:/courses";
    }

    // ---- DELETE COURSE ----
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseService.deleteCourse(id);
        redirectAttributes.addFlashAttribute("successMessage", "Course deleted successfully.");
        return "redirect:/courses";
    }
}
