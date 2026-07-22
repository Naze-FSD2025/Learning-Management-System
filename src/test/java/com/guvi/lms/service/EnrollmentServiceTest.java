package com.guvi.lms.service;

import com.guvi.lms.entity.Course;
import com.guvi.lms.entity.Enrollment;
import com.guvi.lms.entity.User;
import com.guvi.lms.repository.CourseRepository;
import com.guvi.lms.repository.EnrollmentRepository;
import com.guvi.lms.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    void shouldEnrollStudent() {

        User student = new User();
        student.setId(3L);
        student.setEmail("student@gmail.com");

        Course course = new Course();
        course.setId(1L);

        when(userRepository.findByEmail(
                "student@gmail.com"))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        when(enrollmentRepository
                .findByStudentIdAndCourseId(
                        3L,
                        1L))
                .thenReturn(Optional.empty());

        String result =
                enrollmentService.enrollCourse(
                        1L,
                        "student@gmail.com");

        assertEquals(
                "Enrollment Successful",
                result);

        verify(enrollmentRepository)
                .save(any(Enrollment.class));
    }

    @Test
    void shouldReturnAlreadyEnrolled() {

        User student = new User();
        student.setId(3L);

        Course course = new Course();
        course.setId(1L);

        Enrollment enrollment =
                new Enrollment();

        when(userRepository.findByEmail(
                "student@gmail.com"))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        when(enrollmentRepository
                .findByStudentIdAndCourseId(
                        3L,
                        1L))
                .thenReturn(
                        Optional.of(enrollment));

        String result =
                enrollmentService.enrollCourse(
                        1L,
                        "student@gmail.com");

        assertEquals(
                "Already Enrolled",
                result);

        verify(enrollmentRepository,
                never())
                .save(any(Enrollment.class));
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {

        when(userRepository.findByEmail(
                "student@gmail.com"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,

                        () -> enrollmentService
                                .enrollCourse(
                                        1L,
                                        "student@gmail.com"));

        assertEquals(
                "Student not found",
                exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {

        User student = new User();
        student.setId(3L);

        when(userRepository.findByEmail(
                "student@gmail.com"))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,

                        () -> enrollmentService
                                .enrollCourse(
                                        1L,
                                        "student@gmail.com"));

        assertEquals(
                "Course not found",
                exception.getMessage());
    }

}