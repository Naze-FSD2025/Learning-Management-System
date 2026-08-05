package com.guvi.lms.controller;

import com.guvi.lms.entity.Course;
import com.guvi.lms.entity.LessonProgress;
import com.guvi.lms.entity.User;
import com.guvi.lms.repository.CourseRepository;
import com.guvi.lms.repository.EnrollmentRepository;
import com.guvi.lms.repository.LessonProgressRepository;
import com.guvi.lms.repository.UserRepository;
import com.guvi.lms.service.CourseService;
import com.guvi.lms.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminDashboardController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseService courseService;
    private final LessonProgressRepository lessonProgressRepository;
    private final ProgressService progressService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {

        List<User> users = userRepository.findAll();
        List<Course> courses = courseRepository.findAll();

        List<LessonProgress> progressList =
                lessonProgressRepository.findAll();



        long enrollmentCount = enrollmentRepository.count();

        model.addAttribute("users", users);
        model.addAttribute("courses", courses);
        model.addAttribute("totalUsers", users.size());
        model.addAttribute("totalCourses", courses.size());
        model.addAttribute("totalEnrollments", enrollmentCount);
        model.addAttribute("progressList", progressList);
        model.addAttribute("progressReports", progressService.getProgressReport());

        return "admin-dashboard";
    }

    @PostMapping("/admin/course/{id}/approve")
    public String approveCourse(
            @PathVariable Long id) {

        courseService.approveCourse(id);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/course/{id}/delete")
    public String deleteCourse(
            @PathVariable Long id) {

        courseService.deleteCourse(id);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Prevent deleting admins
        if (user.getRole().name().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        // If instructor owns courses, don't delete
        if (user.getRole().name().equals("INSTRUCTOR")) {

            long courseCount = courseRepository.countByInstructor(user);

            if (courseCount > 0) {
                return "redirect:/admin/dashboard";
            }
        }

        userRepository.delete(user);

        return "redirect:/admin/dashboard";
    }

}