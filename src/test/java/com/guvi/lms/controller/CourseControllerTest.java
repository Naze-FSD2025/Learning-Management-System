package com.guvi.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guvi.lms.dto.CourseRequest;
import com.guvi.lms.entity.Course;
import com.guvi.lms.security.CustomUserDetailsService;
import com.guvi.lms.security.JwtAuthenticationFilter;
import com.guvi.lms.security.JwtService;
import com.guvi.lms.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CourseService courseService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldGetApprovedCourses() throws Exception {

        Course course = new Course();
        course.setId(1L);
        course.setTitle("Java Full Stack");

        when(courseService.getApprovedCourses())
                .thenReturn(List.of(course));

        mockMvc.perform(
                        get("/api/student/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title")
                        .value("Java Full Stack"));
    }

    @Test
    void shouldApproveCourse() throws Exception {

        Course course = new Course();
        course.setId(1L);
        course.setApproved(true);

        when(courseService.approveCourse(1L))
                .thenReturn(course);

        mockMvc.perform(
                        put("/api/admin/courses/1/approve"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.approved")
                        .value(true));
    }

    @Test
    void shouldDeleteCourse() throws Exception {

        when(courseService.deleteCourse(1L))
                .thenReturn("Course Deleted Successfully");

        mockMvc.perform(
                        delete("/api/instructor/courses/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Course Deleted Successfully"));
    }
}