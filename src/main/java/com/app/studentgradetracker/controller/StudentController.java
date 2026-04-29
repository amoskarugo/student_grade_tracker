package com.app.studentgradetracker.controller;


import com.app.studentgradetracker.ApiResponse.ApiResponse;
import com.app.studentgradetracker.Mappers.impl.StudentMapper;
import com.app.studentgradetracker.dto.StudentDto;
import com.app.studentgradetracker.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    public final StudentService studentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(studentService.findAll(), "Students retrieved"));
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<StudentDto>> create(@Valid @RequestBody StudentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(studentService.create(dto), "Student created"));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(studentService.findById(id), "Student retrieved"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> update(
            @PathVariable Long id, @Valid @RequestBody StudentDto dto) {
        return ResponseEntity.ok(ApiResponse.success(studentService.update(id, dto), "Student updated"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Student deleted"));
    }
}
