package com.guvi.lms.service;

import com.guvi.lms.entity.MediaFile;
import com.guvi.lms.repository.MediaFileRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final MediaFileRepository mediaFileRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public MediaFile uploadFile(
            MultipartFile file) throws Exception {

        Path uploadPath =
                Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName =
                file.getOriginalFilename();

        Path filePath =
                uploadPath.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING);

        MediaFile mediaFile =
                new MediaFile();

        mediaFile.setFileName(fileName);
        mediaFile.setFileType(
                file.getContentType());
        mediaFile.setFilePath(
                filePath.toString());

        return mediaFileRepository
                .save(mediaFile);
    }


    public List<MediaFile> getAllFiles() {

        return mediaFileRepository.findAll();
    }

    public String deleteFile(Long id) throws Exception {

        MediaFile mediaFile = mediaFileRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "File not found"));

        Path filePath =
                Paths.get(mediaFile.getFilePath());

        Files.deleteIfExists(filePath);

        mediaFileRepository.delete(mediaFile);

        return "File Deleted Successfully";
    }

    public Resource downloadFile(Long id)
            throws Exception {

        MediaFile mediaFile =
                mediaFileRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "File not found"));

        Path path =
                Paths.get(mediaFile.getFilePath());

        return new UrlResource(
                path.toUri());
    }
}