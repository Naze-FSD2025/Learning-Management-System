package com.guvi.lms.controller;

import com.guvi.lms.dto.CourseRequest;
import com.guvi.lms.dto.LessonRequest;
import com.guvi.lms.dto.RegisterRequest;
import com.guvi.lms.service.AuthService;
import com.guvi.lms.service.CourseService;
import com.guvi.lms.service.FileStorageService;
import com.guvi.lms.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PageController {

    private final CourseService courseService;
    private final LessonService lessonService ;
    private final FileStorageService  fileStorageService;
    private final AuthService authService;

    public PageController(CourseService courseService, LessonService lessonService, FileStorageService fileStorageService, AuthService authService) {
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.authService = authService;
        this.fileStorageService = fileStorageService;
    }

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

//    @GetMapping("/student/dashboard")
//    public String studentDashboard() {
//        return "student-dashboard";
//    }
//
//    @GetMapping("/instructor/dashboard")
//    public String instructorDashboard() {
//        return "instructor-dashboard";
//    }
//
//    @GetMapping("/admin/dashboard")
//    public String adminDashboard() {
//        return "admin-dashboard";
//    }

}