package com.guvi.lms.controller;

import com.guvi.lms.entity.User;
import com.guvi.lms.repository.UserRepository;

import com.guvi.lms.service.CourseService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final CourseService courseService;

    @GetMapping("/users")
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(
            @PathVariable Long id) {

        userRepository.deleteById(id);

        return "User Deleted";
    }


}