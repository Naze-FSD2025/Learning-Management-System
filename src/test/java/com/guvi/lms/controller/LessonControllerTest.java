package com.guvi.lms.controller;

import com.guvi.lms.security.JwtAuthenticationFilter;
import com.guvi.lms.security.JwtService;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guvi.lms.dto.LessonRequest;
import com.guvi.lms.entity.Lesson;
import com.guvi.lms.service.LessonService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LessonController.class)
@AutoConfigureMockMvc(addFilters = false)
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LessonService lessonService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

//    @MockitoBean
//    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldGetLessons() throws Exception {

        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setTitle("Spring Boot Basics");

        when(lessonService.getLessonsByCourse(1L))
                .thenReturn(List.of(lesson));

        mockMvc.perform(
                        get("/api/student/courses/1/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title")
                        .value("Spring Boot Basics"));
    }

    @Test
    void shouldAddLesson() throws Exception {

        LessonRequest request = new LessonRequest();
        request.setTitle("Lesson 1");

        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setTitle("Lesson 1");

        when(lessonService.addLesson(1L, request))
                .thenReturn(lesson);

        mockMvc.perform(
                        post("/api/instructor/courses/1/lessons")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title")
                        .value("Lesson 1"));
    }

    @Test
    void shouldUpdateLesson() throws Exception {

        LessonRequest request = new LessonRequest();
        request.setTitle("Updated Lesson");

        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setTitle("Updated Lesson");

        when(lessonService.updateLesson(1L, request))
                .thenReturn(lesson);

        mockMvc.perform(
                        put("/api/instructor/lessons/1")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title")
                        .value("Updated Lesson"));
    }

    @Test
    void shouldDeleteLesson() throws Exception {

        when(lessonService.deleteLesson(1L))
                .thenReturn("Lesson Deleted Successfully");

        mockMvc.perform(
                        delete("/api/instructor/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Lesson Deleted Successfully"));
    }
}