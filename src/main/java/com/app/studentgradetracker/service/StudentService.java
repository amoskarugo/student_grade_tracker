package com.app.studentgradetracker.service;


import com.app.studentgradetracker.Mappers.impl.StudentMapper;
import com.app.studentgradetracker.dto.StudentDto;
import com.app.studentgradetracker.model.Student;
import com.app.studentgradetracker.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentMapper studentMapper;
    private final StudentRepo studentRepo;


//    public StudentDto
    public Student save(Student student){
        return studentRepo.create(student);
    }

    public List<StudentDto> selectAll(){
        return studentRepo.findAll().stream().map(studentMapper::mapFrom).toList();
    }
}
