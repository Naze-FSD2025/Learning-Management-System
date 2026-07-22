package com.guvi.lms.repository;

import com.guvi.lms.entity.Course;
import com.guvi.lms.entity.Lesson;
import com.guvi.lms.entity.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository
        extends JpaRepository<Lesson, Long> {

    List<Lesson> findByCourseId(Long courseId);

    long countByCourseId(Long courseId);

    void deleteByCourseId(Long courseId);
    List<Lesson> findByCourse(Course course);
}