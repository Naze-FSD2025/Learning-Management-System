package com.guvi.lms.repository;

import com.guvi.lms.entity.*;
import com.guvi.lms.entity.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LessonProgressRepository
        extends JpaRepository<LessonProgress, Long> {

    Optional<LessonProgress>
    findByStudentIdAndLessonId(
            Long studentId,
            Long lessonId);

//    List<LessonProgress>
//    findByStudentId(Long studentId);
    long countByStudentIdAndLessonCourseIdAndCompletedTrue(
            Long studentId,
            Long courseId);

    long countByStudentIdAndCompletedTrue(Long studentId);

    List<LessonProgress> findAll();
}