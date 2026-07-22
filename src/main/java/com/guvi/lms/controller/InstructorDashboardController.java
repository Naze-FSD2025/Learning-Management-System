package com.guvi.lms.controller;

import com.guvi.lms.entity.Course;
import com.guvi.lms.repository.CourseRepository;
import com.guvi.lms.service.CourseService;
import com.guvi.lms.service.FileStorageService;
import com.guvi.lms.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class InstructorDashboardController {

    private final CourseRepository courseRepository;
    private final CourseService  courseService;
    private final LessonService lessonService;
    private final FileStorageService fileStorageService;

    @GetMapping("/instructor/dashboard")
    public String dashboard(Model model) {

        // Get courses created by logged-in instructor
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("totalCourses", courses.size());

        model.addAttribute("courses", courses);

        // Count lessons of instructor courses
        int lessonCount = courses.stream()
                .mapToInt(course -> lessonService
                        .getLessonsByCourse(course.getId())
                        .size())
                .sum();

        model.addAttribute("lessonCount", lessonCount);


        // Count uploaded files
        model.addAttribute(
                "fileCount",
                fileStorageService.getAllFiles().size()
        );

        return "instructor-dashboard";
    }
}
