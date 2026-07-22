package com.guvi.lms.controller;

import com.guvi.lms.service.ProgressService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping(
            "/api/student/lessons/{lessonId}/complete")
    public String completeLesson(
            @PathVariable Long lessonId,
            Authentication authentication) {

        return progressService.completeLesson(
                lessonId,
                authentication.getName());
    }

    @GetMapping(
            "/api/student/courses/{courseId}/progress")
    public double getProgress(
            @PathVariable Long courseId,
            Authentication authentication) {

        return progressService.getCourseProgress(
                courseId,
                authentication.getName());
    }
}