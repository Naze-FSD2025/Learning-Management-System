package com.guvi.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProgressReportDto {

    private String studentName;
    private String courseTitle;
    private int progressPercentage;
}