package com.guvi.lms.service;

import com.guvi.lms.dto.LessonRequest;
import com.guvi.lms.entity.Course;
import com.guvi.lms.entity.Lesson;
import com.guvi.lms.repository.CourseRepository;
import com.guvi.lms.repository.LessonRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    // Add Lesson
    public Lesson addLesson(
            Long courseId,
            LessonRequest request) {

        Course course = courseRepository
                .findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Course not found"));

        Lesson lesson = new Lesson();

        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        lesson.setVideoUrl(request.getVideoUrl());
        lesson.setPdfUrl(request.getPdfUrl());
        lesson.setCourse(course);

        return lessonRepository.save(lesson);
    }

    // View Lessons
    public List<Lesson> getLessonsByCourse(
            Long courseId) {

        return lessonRepository
                .findByCourseId(courseId);
    }

    // Update Lesson
    public Lesson updateLesson(
            Long lessonId,
            LessonRequest request) {

        Lesson lesson = lessonRepository
                .findById(lessonId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Lesson not found"));

        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        lesson.setVideoUrl(request.getVideoUrl());
        lesson.setPdfUrl(request.getPdfUrl());

        return lessonRepository.save(lesson);
    }

    // Delete Lesson
    public String deleteLesson(Long lessonId) {

        lessonRepository.deleteById(lessonId);

        return "Lesson Deleted Successfully";
    }
}