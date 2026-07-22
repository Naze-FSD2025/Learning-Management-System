package com.guvi.lms.repository;

import com.guvi.lms.entity.Course;
import com.guvi.lms.entity.Enrollment;
import com.guvi.lms.entity.Role;
import com.guvi.lms.entity.User;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EnrollmentRepositoryTest {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void shouldCheckEnrollmentExists() {

        User student = new User();
        student.setEmail("student@test.com");
        student.setName("student");
        student.setPassword("password");

        student = userRepository.save(student);

        Course course = new Course();
        course.setTitle("Java Full Stack");
        course.setApproved(true);

        course = courseRepository.save(course);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        enrollmentRepository.save(enrollment);

        boolean exists =
                enrollmentRepository.existsByStudentAndCourse(
                        student,
                        course);

        assertTrue(exists);
    }

    @Test
    void shouldFindByStudentIdAndCourseId() {

        User student = new User();
        student.setName("student");
        student.setEmail("student@test.com");
        student.setPassword("password");
        student.setRole(Role.STUDENT);

        student = userRepository.save(student);

        Course course = new Course();
        course.setTitle("Spring Boot");
        course.setApproved(true);

        course = courseRepository.save(course);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        enrollmentRepository.save(enrollment);

        Optional<Enrollment> result =
                enrollmentRepository.findByStudentIdAndCourseId(
                        student.getId(),
                        course.getId());

        assertTrue(result.isPresent());
    }

    @Test
    void shouldFindAllEnrollmentsByStudentId() {

        User student = new User();
        student.setName("student");
        student.setEmail("student@test.com");
        student.setPassword("password");
        student.setRole(Role.STUDENT);

        student = userRepository.save(student);

        Course course = new Course();
        course.setTitle("Microservices");
        course.setApproved(true);

        course = courseRepository.save(course);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        enrollmentRepository.save(enrollment);

        List<Enrollment> enrollments =
                enrollmentRepository.findByStudentId(
                        student.getId());

        assertEquals(1, enrollments.size());
    }
}