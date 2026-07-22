package com.guvi.lms.dto;

import lombok.Data;

@Data
public class LessonRequest {

    private String title;

    private String content;

    private String videoUrl;

    private String pdfUrl;
}