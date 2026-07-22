package com.guvi.lms.controller;

import com.guvi.lms.dto.LessonRequest;
import com.guvi.lms.entity.Lesson;
import com.guvi.lms.service.LessonService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    // Instructor Add Lesson
    @PostMapping(
            "/api/instructor/courses/{courseId}/lessons")
    public Lesson addLesson(
            @PathVariable Long courseId,
            @RequestBody LessonRequest request) {
        System.out.println("ADD LESSON API HIT");
        return lessonService.addLesson(
                courseId,
                request);
    }

    // Student View Lessons
    @GetMapping(
            "/api/student/courses/{courseId}/lessons")
    public List<Lesson> getLessons(
            @PathVariable Long courseId) {

        return lessonService
                .getLessonsByCourse(courseId);
    }

    // Instructor Update Lesson
    @PutMapping(
            "/api/instructor/lessons/{lessonId}")
    public Lesson updateLesson(
            @PathVariable Long lessonId,
            @RequestBody LessonRequest request) {

        return lessonService
                .updateLesson(
                        lessonId,
                        request);
    }

    // Instructor Delete Lesson
    @DeleteMapping(
            "/api/instructor/lessons/{lessonId}")
    public String deleteLesson(
            @PathVariable Long lessonId) {

        return lessonService
                .deleteLesson(lessonId);
    }

    @PostMapping("/api/instructor/test-post")
    public String testPost() {
        return "POST WORKING";
    }


}