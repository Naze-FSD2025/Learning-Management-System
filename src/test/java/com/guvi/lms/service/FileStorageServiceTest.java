package com.guvi.lms.service;

import com.guvi.lms.entity.MediaFile;
import com.guvi.lms.repository.MediaFileRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileStorageServiceTest {

    @Mock
    private MediaFileRepository mediaFileRepository;

    @InjectMocks
    private FileStorageService fileStorageService;

    //Upload File Successfully
    @Test
    void shouldUploadFile() throws Exception {

        ReflectionTestUtils.setField(
                fileStorageService,
                "uploadDir",
                "uploads-test");

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "springboot.pdf",
                        "application/pdf",
                        "test content".getBytes());

        MediaFile savedFile =
                new MediaFile();

        savedFile.setId(1L);
        savedFile.setFileName("springboot.pdf");

        when(mediaFileRepository.save(
                any(MediaFile.class)))
                .thenReturn(savedFile);

        MediaFile result =
                fileStorageService.uploadFile(file);

        assertNotNull(result);
        assertEquals(
                "springboot.pdf",
                result.getFileName());

        verify(mediaFileRepository)
                .save(any(MediaFile.class));
    }
    //Get All Files
    @Test
    void shouldReturnAllFiles() {

        MediaFile file1 =
                new MediaFile();

        MediaFile file2 =
                new MediaFile();

        when(mediaFileRepository.findAll())
                .thenReturn(
                        List.of(file1, file2));

        List<MediaFile> files =
                fileStorageService.getAllFiles();

        assertEquals(2, files.size());

        verify(mediaFileRepository)
                .findAll();
    }
    //Delete File Successfully
    @Test
    void shouldDeleteFile() throws Exception {

        MediaFile mediaFile =
                new MediaFile();

        mediaFile.setId(1L);
        mediaFile.setFilePath(
                "uploads-test/sample.pdf");

        when(mediaFileRepository.findById(1L))
                .thenReturn(
                        Optional.of(mediaFile));

        String result =
                fileStorageService.deleteFile(1L);

        assertEquals(
                "File Deleted Successfully",
                result);

        verify(mediaFileRepository)
                .delete(mediaFile);
    }
    //File Not Found During Delete
    @Test
    void shouldThrowExceptionWhenDeletingMissingFile() {

        when(mediaFileRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,

                        () -> fileStorageService
                                .deleteFile(1L));

        assertEquals(
                "File not found",
                exception.getMessage());
    }

    //Download File Successfully

    @Test
    void shouldDownloadFile() throws Exception {

        MediaFile mediaFile =
                new MediaFile();

        mediaFile.setId(1L);
        mediaFile.setFilePath(
                "uploads/sample.pdf");

        when(mediaFileRepository.findById(1L))
                .thenReturn(
                        Optional.of(mediaFile));

        assertNotNull(
                fileStorageService
                        .downloadFile(1L));
    }

    //File Not Found During Download

    @Test
    void shouldThrowExceptionWhenDownloadingMissingFile() {

        when(mediaFileRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,

                        () -> fileStorageService
                                .downloadFile(1L));

        assertEquals(
                "File not found",
                exception.getMessage());
    }

}
