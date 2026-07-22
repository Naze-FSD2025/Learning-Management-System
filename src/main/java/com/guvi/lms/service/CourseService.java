package com.guvi.lms.service;

import com.guvi.lms.dto.CourseRequest;
import com.guvi.lms.entity.Course;
import com.guvi.lms.entity.User;
import com.guvi.lms.repository.CourseRepository;
import com.guvi.lms.repository.LessonRepository;
import com.guvi.lms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    // Create Course
    public Course createCourse(
            CourseRequest request,
            String instructorEmail) {

        User instructor = userRepository
                .findByEmail(instructorEmail)
                .orElseThrow(() ->
                        new RuntimeException("Instructor not found"));

        Course course = new Course();

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setInstructor(instructor);
        course.setApproved(false);

        return courseRepository.save(course);
    }

    // Approve Course
    public Course approveCourse(Long courseId) {

        Course course = courseRepository
                .findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        course.setApproved(true);

        return courseRepository.save(course);
    }


    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get Approved Courses
    public List<Course> getApprovedCourses() {

        return courseRepository.findByApprovedTrue();
    }

    // Delete Course
    public String deleteCourse(Long courseId) {

        Course course = courseRepository
                .findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        courseRepository.delete(course);

        return "Course Deleted Successfully";
    }


}