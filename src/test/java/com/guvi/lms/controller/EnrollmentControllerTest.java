package com.guvi.lms.controller;

import com.guvi.lms.service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentControllerTest {

    @InjectMocks
    private EnrollmentController enrollmentController;

    @Mock
    private EnrollmentService enrollmentService;

    @Mock
    private Authentication authentication;

    @Test
    void testEnrollCourse() {

        Long courseId = 1L;
        String email = "student@gmail.com";

        when(authentication.getName())
                .thenReturn(email);

        when(enrollmentService.enrollCourse(
                courseId,
                email))
                .thenReturn("Enrollment Successful");

        String result =
                enrollmentController.enrollCourse(
                        courseId,
                        authentication);

        assertEquals(
                "Enrollment Successful",
                result);

        verify(enrollmentService)
                .enrollCourse(
                        courseId,
                        email);
    }

    @Test
    void testGetEnrollments() {

        List<String> result =
                enrollmentController.getEnrollments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("TEST", result.get(0));
    }
}