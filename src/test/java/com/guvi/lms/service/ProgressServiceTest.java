package com.guvi.lms.service;

import com.guvi.lms.entity.*;
import com.guvi.lms.repository.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgressServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private LessonProgressRepository progressRepository;

    @InjectMocks
    private ProgressService progressService;

    @Test
    void shouldCompleteLesson() {

        User student = new User();
        student.setId(3L);
        student.setEmail("student@gmail.com");

        Lesson lesson = new Lesson();
        lesson.setId(2L);

        when(userRepository.findByEmail(
                "student@gmail.com"))
                .thenReturn(Optional.of(student));

        when(lessonRepository.findById(2L))
                .thenReturn(Optional.of(lesson));

        when(progressRepository
                .findByStudentIdAndLessonId(
                        3L,
                        2L))
                .thenReturn(Optional.empty());

        String result =
                progressService.completeLesson(
                        2L,
                        "student@gmail.com");

        assertEquals(
                "Lesson Completed Successfully",
                result);

        verify(progressRepository)
                .save(any(LessonProgress.class));
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {

        when(userRepository.findByEmail(
                "student@gmail.com"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,

                        () -> progressService
                                .completeLesson(
                                        2L,
                                        "student@gmail.com"));

        assertEquals(
                "Student not found",
                exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenLessonNotFound() {

        User student = new User();
        student.setId(3L);

        when(userRepository.findByEmail(
                "student@gmail.com"))
                .thenReturn(Optional.of(student));

        when(lessonRepository.findById(2L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,

                        () -> progressService
                                .completeLesson(
                                        2L,
                                        "student@gmail.com"));

        assertEquals(
                "Lesson not found",
                exception.getMessage());
    }

    @Test
    void shouldUpdateExistingProgress() {

        User student = new User();
        student.setId(3L);

        Lesson lesson = new Lesson();
        lesson.setId(2L);

        LessonProgress progress =
                new LessonProgress();

        when(userRepository.findByEmail(
                "student@gmail.com"))
                .thenReturn(Optional.of(student));

        when(lessonRepository.findById(2L))
                .thenReturn(Optional.of(lesson));

        when(progressRepository
                .findByStudentIdAndLessonId(
                        3L,
                        2L))
                .thenReturn(Optional.of(progress));

        String result =
                progressService.completeLesson(
                        2L,
                        "student@gmail.com");

        assertEquals(
                "Lesson Completed Successfully",
                result);

        verify(progressRepository)
                .save(progress);
    }

    @Test
    void shouldCalculateCourseProgress() {

        User student = new User();
        student.setId(3L);

        when(userRepository.findByEmail(
                "student@gmail.com"))
                .thenReturn(Optional.of(student));

        when(lessonRepository
                .countByCourseId(1L))
                .thenReturn(4L);

        when(progressRepository
                .countByStudentIdAndLessonCourseIdAndCompletedTrue(
                        3L,
                        1L))
                .thenReturn(2L);

        double result =
                progressService
                        .getCourseProgress(
                                1L,
                                "student@gmail.com");

        assertEquals(
                50.0,
                result);
    }

    @Test
    void shouldReturnZeroWhenNoLessonsExist() {

        User student = new User();
        student.setId(3L);

        when(userRepository.findByEmail(
                "student@gmail.com"))
                .thenReturn(Optional.of(student));

        when(lessonRepository
                .countByCourseId(1L))
                .thenReturn(0L);

        double result =
                progressService
                        .getCourseProgress(
                                1L,
                                "student@gmail.com");

        assertEquals(
                0.0,
                result);
    }
}