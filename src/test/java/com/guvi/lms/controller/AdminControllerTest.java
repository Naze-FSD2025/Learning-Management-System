package com.guvi.lms.controller;

import com.guvi.lms.entity.User;
import com.guvi.lms.repository.UserRepository;
import com.guvi.lms.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseService courseService;

    @Test
    void testGetAllUsers() {

        User user1 = new User();
        user1.setId(1L);
        user1.setName("John");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Mary");

        List<User> users =
                List.of(user1, user2);

        when(userRepository.findAll())
                .thenReturn(users);

        List<User> result =
                adminController.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("John",
                result.get(0).getName());
        assertEquals("Mary",
                result.get(1).getName());

        verify(userRepository)
                .findAll();
    }

    @Test
    void testDeleteUser() {

        Long userId = 1L;

        doNothing()
                .when(userRepository)
                .deleteById(userId);

        String result =
                adminController.deleteUser(userId);

        assertEquals(
                "User Deleted",
                result);

        verify(userRepository)
                .deleteById(userId);
    }
}