package com.guvi.lms.controller;

import com.guvi.lms.entity.MediaFile;
import com.guvi.lms.service.FileStorageService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MediaController {

    private final FileStorageService fileStorageService;

    @PostMapping(
            "/api/instructor/upload")
    public MediaFile uploadFile(
            @RequestParam("file")
            MultipartFile file)
            throws Exception {

        return fileStorageService
                .uploadFile(file);
    }

    @GetMapping("/api/instructor/files")
    public List<MediaFile> getAllFiles() {

        return fileStorageService.getAllFiles();
    }

    @DeleteMapping("/api/instructor/files/{id}")
    public String deleteFile(
            @PathVariable Long id)
            throws Exception {

        return fileStorageService.deleteFile(id);
    }

    //get files

    @GetMapping("/api/files/download/{id}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long id)
            throws Exception {

        Resource resource =
                fileStorageService
                        .downloadFile(id);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\""
                                + resource.getFilename()
                                + "\"")

                .header(
                        HttpHeaders.CONTENT_TYPE,
                        "application/pdf")
                .body(resource);
    }
}