package com.app.studentgradetracker.dto;
import jakarta.validation.constraints.*;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradeDto {
    private Long   id;

    @NotNull(message = "Student ID is required")
    private Long   student_id;

    @NotNull(message = "Course ID is required")
    private Long   course_id;

    @NotNull(message = "Grade is required")
    @DecimalMin(value = "0.0", message = "Grade must be at least 0")
    @DecimalMax(value = "100.0", message = "Grade must not exceed 100")
    private Double grade;

    @NotBlank(message = "Semester is required")
    private String semester;
}
