package com.app.studentgradetracker.controller;


import com.app.studentgradetracker.Mappers.impl.StudentMapper;
import com.app.studentgradetracker.dto.StudentDto;
import com.app.studentgradetracker.model.Student;
import com.app.studentgradetracker.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController {

    public final StudentService studentService;
    public final StudentMapper studentMapper;

    @PostMapping("/create")
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto){
        Student student = studentMapper.mapTo(studentDto);
        Student savedStudent = studentService.save(student);
        return ResponseEntity.ok(studentMapper.mapFrom(savedStudent));
    }
}
