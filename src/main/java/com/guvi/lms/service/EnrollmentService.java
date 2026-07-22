package com.guvi.lms.service;

import com.guvi.lms.entity.*;
import com.guvi.lms.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public String enrollCourse(
            Long courseId,
            String studentEmail) {

        User student = userRepository
                .findByEmail(studentEmail)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        Course course = courseRepository
                .findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        boolean alreadyEnrolled =
                enrollmentRepository
                        .findByStudentIdAndCourseId(
                                student.getId(),
                                courseId)
                        .isPresent();

        if(alreadyEnrolled) {
            return "Already Enrolled";
        }

        Enrollment enrollment =
                new Enrollment();

        enrollment.setStudent(student);
        enrollment.setCourse(course);

        enrollmentRepository.save(enrollment);

        return "Enrollment Successful";
    }

    public List<Enrollment>
    getStudentEnrollments(
            String studentEmail) {

        User student = userRepository
                .findByEmail(studentEmail)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        return enrollmentRepository
                .findByStudentId(
                        student.getId());
    }
}