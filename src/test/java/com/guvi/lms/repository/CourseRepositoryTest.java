package com.guvi.lms.repository;

import com.guvi.lms.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void shouldFindApprovedCourses() {

        Course approvedCourse = new Course();
        approvedCourse.setTitle("Java Full Stack");
        approvedCourse.setApproved(true);

        courseRepository.save(approvedCourse);

        List<Course> courses =
                courseRepository.findByApprovedTrue();

        assertTrue(courses.size() >= 1);

        assertTrue(
                courses.stream()
                        .anyMatch(course ->
                                course.getTitle()
                                        .equals("Java Full Stack")));
    }
}