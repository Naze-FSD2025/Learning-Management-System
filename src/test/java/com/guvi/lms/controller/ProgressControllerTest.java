package com.guvi.lms.controller;

import com.guvi.lms.service.ProgressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgressControllerTest {

    @InjectMocks
    private ProgressController progressController;

    @Mock
    private ProgressService progressService;

    @Mock
    private Authentication authentication;

    @Test
    void testCompleteLesson() {

        Long lessonId = 1L;
        String email = "student@gmail.com";

        when(authentication.getName())
                .thenReturn(email);

        when(progressService.completeLesson(
                lessonId,
                email))
                .thenReturn(
                        "Lesson Completed Successfully");

        String result =
                progressController.completeLesson(
                        lessonId,
                        authentication);

        assertEquals(
                "Lesson Completed Successfully",
                result);

        verify(progressService)
                .completeLesson(
                        lessonId,
                        email);
    }

    @Test
    void testGetProgress() {

        Long courseId = 1L;
        String email = "student@gmail.com";

        when(authentication.getName())
                .thenReturn(email);

        when(progressService.getCourseProgress(
                courseId,
                email))
                .thenReturn(75.0);

        double result =
                progressController.getProgress(
                        courseId,
                        authentication);

        assertEquals(75.0, result);

        verify(progressService)
                .getCourseProgress(
                        courseId,
                        email);
    }
}