package com.guvi.lms.controller;

import com.guvi.lms.dto.CourseRequest;
import com.guvi.lms.entity.Course;
import com.guvi.lms.service.CourseService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // Instructor creates course
    @PostMapping("/api/instructor/courses")
    public Course createCourse(
            @RequestBody CourseRequest request,
            Authentication authentication) {

        String instructorEmail =
                authentication.getName();

        return courseService.createCourse(
                request,
                instructorEmail);
    }

    // Admin approves course
    @PutMapping("/api/admin/courses/{id}/approve")
    public Course approveCourse(
            @PathVariable Long id) {

        return courseService.approveCourse(id);
    }

    // Student views approved courses
    @GetMapping("/api/student/courses")
    public List<Course> getApprovedCourses() {

        return courseService.getApprovedCourses();
    }

    // Instructor deletes course
    @DeleteMapping("/api/instructor/courses/{id}")
    public String deleteCourse(
            @PathVariable Long id) {

        return courseService.deleteCourse(id);
    }
}