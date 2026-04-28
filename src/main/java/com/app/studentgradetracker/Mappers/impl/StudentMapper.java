package com.app.studentgradetracker.Mappers.impl;

import com.app.studentgradetracker.Mappers.Mapper;
import com.app.studentgradetracker.dto.StudentDto;
import com.app.studentgradetracker.model.Student;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class StudentMapper implements Mapper<StudentDto, Student>, RowMapper<Student> {

    private final ModelMapper modelMapper;

    public StudentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Student.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .enrolled_at(rs.getDate("enrolled_at").toLocalDate())
                .build();
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
