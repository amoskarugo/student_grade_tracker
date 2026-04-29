package com.app.studentgradetracker.controller;


import com.app.studentgradetracker.ApiResponse.ApiResponse;
import com.app.studentgradetracker.dto.GpaResult;
import com.app.studentgradetracker.dto.GradeDto;
import com.app.studentgradetracker.service.GradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<ApiResponse<GradeDto>> record(@Valid @RequestBody GradeDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(gradeService.recordGrade(dto), "Grade recorded"));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getByStudent(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                gradeService.getGradesWithDetails(studentId), "Grades retrieved"));
    }

    @GetMapping("/student/{studentId}/gpa")
    public ResponseEntity<ApiResponse<List<GpaResult>>> getGpa(@PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                gradeService.getGpa(studentId), "GPA calculated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Grade deleted"));
    }
}