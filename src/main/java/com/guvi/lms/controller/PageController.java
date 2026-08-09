package com.guvi.lms.controller;

import com.guvi.lms.dto.CourseRequest;
import com.guvi.lms.dto.LessonRequest;
import com.guvi.lms.dto.RegisterRequest;
import com.guvi.lms.entity.User;
import com.guvi.lms.repository.UserRepository;
import com.guvi.lms.service.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class PageController {

    private final CourseService courseService;
    private final LessonService lessonService ;
    private final FileStorageService  fileStorageService;
    private final AuthService authService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/instructor/course/create")
    public String createCoursePage() {

        return "create-course";
    }

    @PostMapping("/instructor/course/create")
    public String createCourse(
            @RequestParam String title,
            @RequestParam String description) {

        CourseRequest request = new CourseRequest();

        request.setTitle(title);
        request.setDescription(description);

        courseService.createCourse(
                request,
                "instructor@gmail.com");

        return "redirect:/instructor/dashboard";
    }

    @GetMapping("/instructor/course/{courseId}/lesson")
    public String addLessonPage(
            @PathVariable Long courseId,
            Model model) {

        model.addAttribute("courseId", courseId);

        return "add-lesson";
    }
    // Add Lesson Submit
    @PostMapping("/instructor/course/{courseId}/lesson")
    public String addLesson(
            @PathVariable Long courseId,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String videoUrl,
            @RequestParam String pdfUrl) {

        LessonRequest request = new LessonRequest();

        request.setTitle(title);
        request.setContent(content);
        request.setVideoUrl(videoUrl);
        request.setPdfUrl(pdfUrl);

        lessonService.addLesson(
                courseId,
                request);

        return "redirect:/instructor/dashboard";
    }

    @GetMapping("/instructor/upload")
    public String uploadPage() {

        return "upload-file";
    }

    @GetMapping("/instructor/files")
    public String filesPage(Model model) {

        model.addAttribute(
                "files",
                fileStorageService.getAllFiles());

        return "files";
    }

    @PostMapping("/instructor/upload")
    public String uploadFile(
            @RequestParam("file")
            MultipartFile file)
            throws Exception {

        fileStorageService.uploadFile(file);

        return "redirect:/instructor/dashboard";
    }

    @PostMapping("/instructor/files/delete/{id}")
    public String deleteFile(
            @PathVariable Long id)
            throws Exception {

        fileStorageService.deleteFile(id);

        return "redirect:/instructor/files";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role) {

        RegisterRequest request = new RegisterRequest();

        request.setName(name);
        request.setEmail(email);
        request.setPassword(password);
        request.setRole(role);

        authService.register(request);

        return "redirect:/login";
    }

    @GetMapping("/student/progress")
    public String progressPage() {
        return "student-progress";
    }


    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestParam String email) {

        String resetLink =
                "http://localhost:8080/reset-password?email=" + email;

        emailService.sendResetEmail(
                email,
                resetLink);

        return "redirect:/forgot-password?sent";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(
            @RequestParam String email,
            Model model) {

        model.addAttribute("email", email);

        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword) {

        if (!password.equals(confirmPassword)) {
            return "redirect:/reset-password?email=" + email + "&error";
        }

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setPassword(
                passwordEncoder.encode(password));

        userRepository.save(user);

        return "redirect:/login?passwordReset";
    }
}