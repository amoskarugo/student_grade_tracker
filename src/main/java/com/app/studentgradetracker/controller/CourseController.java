package com.app.studentgradetracker.controller;

import com.app.studentgradetracker.ApiResponse.ApiResponse;
import com.app.studentgradetracker.dto.CourseDto;
import com.app.studentgradetracker.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(courseService.findAll(), "Courses retrieved"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(courseService.findById(id), "Course retrieved"));
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<ApiResponse<List<CourseDto>>> getByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(ApiResponse.success(
                courseService.findByDepartment(department), "Courses retrieved"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseDto>> create(@Valid @RequestBody CourseDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(courseService.create(dto), "Course created"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> update(
            @PathVariable Long id, @Valid @RequestBody CourseDto dto) {
        return ResponseEntity.ok(ApiResponse.success(courseService.update(id, dto), "Course updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Course deleted"));
    }
}
