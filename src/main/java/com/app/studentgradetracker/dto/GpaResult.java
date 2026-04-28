package com.app.studentgradetracker.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GpaResult {
    private Long   studentId;
    private String studentName;
    private String semester;
    private Double gpa;
    private int    totalCourses;

}
