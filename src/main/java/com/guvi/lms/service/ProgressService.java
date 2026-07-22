package com.guvi.lms.service;

import com.guvi.lms.dto.ProgressReportDto;
import com.guvi.lms.entity.*;
import com.guvi.lms.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final LessonProgressRepository progressRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    // Mark lesson completed
    public String completeLesson(
            Long lessonId,
            String studentEmail) {

        User student = userRepository
                .findByEmail(studentEmail)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found"));

        Lesson lesson = lessonRepository
                .findById(lessonId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Lesson not found"));

        LessonProgress progress =
                progressRepository
                        .findByStudentIdAndLessonId(
                                student.getId(),
                                lessonId)
                        .orElse(new LessonProgress());

        progress.setStudent(student);
        progress.setLesson(lesson);
        progress.setCompleted(true);

        progressRepository.save(progress);

        return "Lesson Completed Successfully";
    }

    // Calculate course progress
    public double getCourseProgress(
            Long courseId,
            String studentEmail) {

        User student = userRepository
                .findByEmail(studentEmail)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found"));

        long totalLessons =
                lessonRepository
                        .countByCourseId(courseId);

        if (totalLessons == 0) {
            return 0;
        }

        long completedLessons =
                progressRepository
                        .countByStudentIdAndLessonCourseIdAndCompletedTrue(
                                student.getId(),
                                courseId);

        return ((double) completedLessons
                / totalLessons) * 100;
    }

    public long getCompletedLessons(String studentEmail) {

        User student = userRepository
                .findByEmail(studentEmail)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found"));

        return progressRepository
                .countByStudentIdAndCompletedTrue(
                        student.getId());
    }

    public List<ProgressReportDto> getProgressReport() {

        List<ProgressReportDto> report = new ArrayList<>();

        List<Enrollment> enrollments =
                enrollmentRepository.findAll();

        for (Enrollment enrollment : enrollments) {

            User student = enrollment.getStudent();
            Course course = enrollment.getCourse();

            int totalLessons =
                    lessonRepository.findByCourse(course).size();

            long completedLessons =
                    progressRepository
                            .findAll()
                            .stream()
                            .filter(lp ->
                                    lp.getStudent().getId()
                                            .equals(student.getId()))
                            .filter(lp ->
                                    lp.getLesson()
                                            .getCourse()
                                            .getId()
                                            .equals(course.getId()))
                            .filter(LessonProgress::isCompleted)
                            .count();

            int percentage = 0;

            if (totalLessons > 0) {

                percentage =
                        (int)((completedLessons * 100)
                                / totalLessons);
            }

            report.add(
                    new ProgressReportDto(
                            student.getName(),
                            course.getTitle(),
                            percentage
                    )
            );
        }

        return report;
    }
}