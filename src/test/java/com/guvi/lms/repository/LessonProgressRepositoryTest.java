package com.guvi.lms.repository;

import com.guvi.lms.entity.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class LessonProgressRepositoryTest {


    @Autowired
    private LessonProgressRepository lessonProgressRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void saveLessonProgressTest() {


        User student = new User();

        student.setName("John");
        student.setEmail("john@test.com");
        student.setPassword("12345");
        student.setRole(Role.STUDENT);



        Course course = new Course();

        course.setTitle("Java Full Stack");
        course.setDescription("Spring Boot Course");
        course.setApproved(true);



        Lesson lesson = new Lesson();

        lesson.setTitle("OOPS Concepts");
        lesson.setContent("Java OOPS");
        lesson.setCourse(course);



        LessonProgress progress =
                new LessonProgress();


        progress.setStudent(student);
        progress.setLesson(lesson);
        progress.setCompleted(true);



        LessonProgress saved =
                lessonProgressRepository.save(progress);



        assertNotNull(saved.getId());
        assertTrue(saved.isCompleted());

    }



    @Test
    void findByStudentIdAndLessonIdTest() {


        User student = new User();

        student.setName("Alice");
        student.setEmail("alice@test.com");
        student.setPassword("12345");
        student.setRole(Role.STUDENT);


        entityManager.persist(student);



        Course course = new Course();

        course.setTitle("Java Full Stack");
        course.setDescription("Spring Boot");


        entityManager.persist(course);



        Lesson lesson = new Lesson();

        lesson.setTitle("Spring Boot");
        lesson.setContent("REST API");
        lesson.setCourse(course);


        entityManager.persist(lesson);



        LessonProgress progress =
                new LessonProgress();


        progress.setStudent(student);
        progress.setLesson(lesson);
        progress.setCompleted(true);



        lessonProgressRepository.save(progress);



        Optional<LessonProgress> result =
                lessonProgressRepository
                        .findByStudentIdAndLessonId(
                                student.getId(),
                                lesson.getId()
                        );


        assertTrue(result.isPresent());

        assertTrue(result.get().isCompleted());

    }




    @Test
    void countCompletedLessonsByStudentAndCourseTest() {


        User student = new User();

        student.setName("David");
        student.setEmail("david@test.com");
        student.setPassword("12345");
        student.setRole(Role.STUDENT);



        Course course = new Course();

        course.setTitle("Spring Boot");



        Lesson lesson1 = new Lesson();

        lesson1.setTitle("REST API");
        lesson1.setCourse(course);



        Lesson lesson2 = new Lesson();

        lesson2.setTitle("Security");
        lesson2.setCourse(course);



        LessonProgress progress1 =
                new LessonProgress();

        progress1.setStudent(student);
        progress1.setLesson(lesson1);
        progress1.setCompleted(true);



        LessonProgress progress2 =
                new LessonProgress();

        progress2.setStudent(student);
        progress2.setLesson(lesson2);
        progress2.setCompleted(true);

        entityManager.persist(student);
        entityManager.persist(course);
        entityManager.persist(lesson1);
        entityManager.persist(lesson2);

        lessonProgressRepository.save(progress1);
        lessonProgressRepository.save(progress2);



        long count =
                lessonProgressRepository
                        .countByStudentIdAndLessonCourseIdAndCompletedTrue(
                                student.getId(),
                                course.getId()
                        );



        assertEquals(2,count);

    }

}