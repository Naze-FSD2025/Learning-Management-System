    package com.guvi.lms.entity;

    import jakarta.persistence.*;
    import lombok.*;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "enrollments")
    public class Enrollment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "student_id")
        private User student;

        @ManyToOne
        @JoinColumn(name = "course_id")
        private Course course;
    }