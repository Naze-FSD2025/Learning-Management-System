package com.guvi.lms.controller;

import com.guvi.lms.entity.Enrollment;
import com.guvi.lms.service.EnrollmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping(
            "/api/student/courses/{courseId}/enroll")
    public String enrollCourse(
            @PathVariable Long courseId,
            Authentication authentication) {

        return enrollmentService.enrollCourse(
                courseId,
                authentication.getName());
    }

//    @GetMapping(
//            "/api/student/enrollments")
//    public List<Enrollment>
//    getEnrollments(
//            Authentication authentication) {
//
//        return enrollmentService
//                .getStudentEnrollments(
//                        authentication.getName());
//    }

    @GetMapping("/api/student/enrollments")
    public List<String> getEnrollments() {
        return List.of("TEST");
    }
}