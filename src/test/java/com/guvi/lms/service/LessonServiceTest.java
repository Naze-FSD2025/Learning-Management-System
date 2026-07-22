package com.guvi.lms.service;

import com.guvi.lms.dto.LessonRequest;
import com.guvi.lms.entity.Course;
import com.guvi.lms.entity.Lesson;
import com.guvi.lms.repository.CourseRepository;
import com.guvi.lms.repository.LessonRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private LessonService lessonService;

    @Test
    void shouldAddLesson() {

        Course course = new Course();
        course.setId(1L);

        LessonRequest request =
                new LessonRequest();

        request.setTitle(
                "Spring Boot Basics");

        request.setContent(
                "Introduction");

        request.setVideoUrl(
                "https://youtube.com/video1");

        request.setPdfUrl(
                "https://example.com/file.pdf");

        Lesson lesson = new Lesson();

        lesson.setTitle(
                request.getTitle());

        when(courseRepository.findById(1L))
                .thenReturn(
                        Optional.of(course));

        when(lessonRepository.save(
                any(Lesson.class)))
                .thenReturn(lesson);

        Lesson savedLesson =
                lessonService.addLesson(
                        1L,
                        request);

        assertNotNull(savedLesson);

        verify(courseRepository)
                .findById(1L);

        verify(lessonRepository)
                .save(any(Lesson.class));
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {

        LessonRequest request =
                new LessonRequest();

        when(courseRepository.findById(1L))
                .thenReturn(
                        Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,

                        () -> lessonService
                                .addLesson(
                                        1L,
                                        request));

        assertEquals(
                "Course not found",
                exception.getMessage());
    }

    @Test
    void shouldReturnLessonsByCourse() {

        Lesson lesson =
                new Lesson();

        lesson.setTitle(
                "Spring Boot Basics");

        when(
                lessonRepository
                        .findByCourseId(1L))
                .thenReturn(
                        List.of(lesson));

        List<Lesson> lessons =
                lessonService
                        .getLessonsByCourse(1L);

        assertEquals(
                1,
                lessons.size());

        assertEquals(
                "Spring Boot Basics",
                lessons.get(0).getTitle());

        verify(lessonRepository)
                .findByCourseId(1L);
    }

    @Test
    void shouldUpdateLesson() {

        Lesson lesson =
                new Lesson();

        lesson.setId(1L);

        LessonRequest request =
                new LessonRequest();

        request.setTitle(
                "Updated Lesson");

        request.setContent(
                "Updated Content");

        when(
                lessonRepository
                        .findById(1L))
                .thenReturn(
                        Optional.of(lesson));

        when(
                lessonRepository
                        .save(any(Lesson.class)))
                .thenReturn(lesson);

        Lesson updatedLesson =
                lessonService
                        .updateLesson(
                                1L,
                                request);

        verify(lessonRepository)
                .findById(1L);

        verify(lessonRepository)
                .save(any(Lesson.class));
    }

    @Test
    void shouldDeleteLesson() {

        String response =
                lessonService
                        .deleteLesson(1L);

        assertEquals(
                "Lesson Deleted Successfully",
                response);

        verify(lessonRepository)
                .deleteById(1L);
    }
}