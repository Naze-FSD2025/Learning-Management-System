package com.guvi.lms.controller;

import com.guvi.lms.repository.CourseRepository;
import com.guvi.lms.service.CourseService;
import com.guvi.lms.service.EnrollmentService;
import com.guvi.lms.service.LessonService;
import com.guvi.lms.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class StudentDashboardController {

    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final LessonService lessonService;
    private final ProgressService progressService;

    @GetMapping("/student/dashboard")
    public String dashboard(
            Model model,
            Authentication authentication) {

        String email = authentication.getName();

        model.addAttribute(
                "availableCourses",
                courseService.getApprovedCourses());

        model.addAttribute(
                "enrollments",
                enrollmentService.getStudentEnrollments(email));

        model.addAttribute(
                "completedLessons",
                progressService.getCompletedLessons(email));

        return "student-dashboard";
    }

    @GetMapping("/student/courses")
    public String browseCourses(Model model) {

        model.addAttribute(
                "courses",
                courseService.getApprovedCourses());

        return "student-courses";
    }

    @PostMapping("/student/enroll/{courseId}")
    public String enrollCourse(
            @PathVariable Long courseId,
            Authentication authentication) {

        enrollmentService.enrollCourse(
                courseId,
                authentication.getName());

        return "redirect:/student/enrollments";
    }

    @GetMapping("/student/enrollments")
    public String myEnrollments(
            Model model,
            Authentication authentication) {

        model.addAttribute(
                "enrollments",
                enrollmentService
                        .getStudentEnrollments(authentication.getName()));

        return "student-enrollments";
    }

    @GetMapping("/student/course/{courseId}")
    public String courseLessons(
            @PathVariable Long courseId,
            Model model) {

        model.addAttribute(
                "lessons",
                lessonService.getLessonsByCourse(courseId));

        return "student-lessons";
    }

    @PostMapping("/student/lesson/{lessonId}/complete")
    public String completeLesson(
            @PathVariable Long lessonId,
            @RequestParam Long courseId,
            Authentication authentication){

        progressService.completeLesson(
                lessonId,
                authentication.getName());

        return "redirect:/student/course/" + courseId;
    }

    @GetMapping("/student/progress/{courseId}")
    public String courseProgress(
            @PathVariable Long courseId,
            Model model,
            Authentication authentication) {

        double progress =
                progressService.getCourseProgress(
                        courseId,
                        authentication.getName());
        progress = Math.round(progress * 100.0) / 100.0;
        model.addAttribute(
                "progress",
                progress);

        return "student-progress";
    }
}