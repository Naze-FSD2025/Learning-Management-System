package com.guvi.lms.repository;

import com.guvi.lms.entity.Course;
import com.guvi.lms.entity.Lesson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE)
class LessonRepositoryTest {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void shouldFindLessonsByCourseId() {

        Course course = new Course();
        course.setTitle("Test Course");
        course.setApproved(true);

        course = courseRepository.save(course);

        Lesson lesson = new Lesson();
        lesson.setTitle("Spring Boot Basics");
        lesson.setCourse(course);

        lessonRepository.save(lesson);

        List<Lesson> lessons =
                lessonRepository.findByCourseId(
                        course.getId());

        assertFalse(lessons.isEmpty());

        assertEquals(
                "Spring Boot Basics",
                lessons.get(0).getTitle());
    }

    @Test
    void shouldCountLessonsByCourseId() {

        Course course = new Course();
        course.setTitle("Java Course");
        course.setApproved(true);

        course = courseRepository.save(course);

        Lesson lesson1 = new Lesson();
        lesson1.setTitle("Lesson 1");
        lesson1.setCourse(course);

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Lesson 2");
        lesson2.setCourse(course);

        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);

        long count =
                lessonRepository.countByCourseId(
                        course.getId());

        assertEquals(2, count);
    }
}