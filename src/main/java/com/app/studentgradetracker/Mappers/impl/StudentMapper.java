package com.app.studentgradetracker.Mappers.impl;

import com.app.studentgradetracker.Mappers.Mapper;
import com.app.studentgradetracker.dto.StudentDto;
import com.app.studentgradetracker.model.Student;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class StudentMapper implements Mapper<StudentDto, Student> {

    private final ModelMapper modelMapper;

    public StudentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Student mapTo(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }

    @Override
    public StudentDto mapFrom(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }
}
