package com.guvi.lms.repository;

import com.guvi.lms.entity.Course;
import com.guvi.lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CourseRepository
        extends JpaRepository<Course, Long> {

    List<Course> findByApprovedTrue();
    long countByInstructor(User instructor);
}