package com.guvi.lms.repository;

import com.guvi.lms.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface EnrollmentRepository
        extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentAndCourse(
            User student,
            Course course);

    Optional<Enrollment>
    findByStudentIdAndCourseId(
            Long studentId,
            Long courseId);

    List<Enrollment>
    findByStudentId(Long studentId);
}