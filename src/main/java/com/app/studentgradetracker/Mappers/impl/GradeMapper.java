package com.app.studentgradetracker.Mappers.impl;

import com.app.studentgradetracker.Mappers.Mapper;
import com.app.studentgradetracker.dto.GradeDto;
import com.app.studentgradetracker.model.Grade;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeMapper implements Mapper<GradeDto, Grade>, RowMapper<Grade> {

    private final ModelMapper modelMapper;

    public GradeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Grade mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Grade.builder()
                .id(rs.getLong("id"))
                .student_id(rs.getLong("student_id"))
                .course_id(rs.getLong("course_id"))
                .grade(rs.getDouble("grade"))
                .semester(rs.getString("semester"))
                .created_at(rs.getTimestamp("created_at").toLocalDateTime())
                .build();
    }


    @Override
    public Grade mapTo(GradeDto gradeDto) {
        return modelMapper.map(gradeDto, Grade.class);
    }

    @Override
    public GradeDto mapFrom(Grade grade) {
        return modelMapper.map(grade, GradeDto.class);
    }
}
