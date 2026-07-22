package com.guvi.lms.service;

import com.guvi.lms.dto.CourseRequest;
import com.guvi.lms.entity.Course;
import com.guvi.lms.entity.Role;
import com.guvi.lms.entity.User;
import com.guvi.lms.repository.CourseRepository;
import com.guvi.lms.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void shouldCreateCourse() {

        User instructor = new User();
        instructor.setId(2L);
        instructor.setName("Instructor");
        instructor.setEmail("instructor@gmail.com");
        instructor.setRole(Role.INSTRUCTOR);

        CourseRequest request = new CourseRequest();
        request.setTitle("Java Full Stack");
        request.setDescription("Spring Boot Course");

        Course course = new Course();
        course.setTitle("Java Full Stack");
        course.setDescription("Spring Boot Course");
        course.setInstructor(instructor);
        course.setApproved(false);

        when(userRepository.findByEmail(
                "instructor@gmail.com"))
                .thenReturn(Optional.of(instructor));

        when(courseRepository.save(any(Course.class)))
                .thenReturn(course);

        Course savedCourse =
                courseService.createCourse(
                        request,
                        "instructor@gmail.com");

        assertNotNull(savedCourse);

        assertEquals(
                "Java Full Stack",
                savedCourse.getTitle());

        assertFalse(savedCourse.isApproved());

        verify(userRepository)
                .findByEmail(
                        "instructor@gmail.com");

        verify(courseRepository)
                .save(any(Course.class));
    }

    @Test
    void shouldThrowExceptionWhenInstructorNotFound() {

        CourseRequest request = new CourseRequest();
        request.setTitle("Java Full Stack");

        when(userRepository.findByEmail(
                "instructor@gmail.com"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> courseService.createCourse(
                                request,
                                "instructor@gmail.com"));

        assertEquals(
                "Instructor not found",
                exception.getMessage());
    }

}