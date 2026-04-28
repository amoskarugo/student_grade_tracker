package com.app.studentgradetracker.controller;


import com.app.studentgradetracker.ApiResponse.ApiResponse;
import com.app.studentgradetracker.Mappers.impl.StudentMapper;
import com.app.studentgradetracker.dto.StudentDto;
import com.app.studentgradetracker.model.Student;
import com.app.studentgradetracker.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    public final StudentService studentService;
    public final StudentMapper studentMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(studentService.findAll(), "Students retrieved"));
    }


    @PostMapping("/create")
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto){
        Student student = studentMapper.mapTo(studentDto);
        Student savedStudent = studentService.save(student);
        return ResponseEntity.ok(studentMapper.mapFrom(savedStudent));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(studentService.findById(id), "Student retrieved"));
    }
}
