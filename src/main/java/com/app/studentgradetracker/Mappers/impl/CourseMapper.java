package com.app.studentgradetracker.Mappers.impl;
import com.app.studentgradetracker.Mappers.Mapper;
import com.app.studentgradetracker.dto.CourseDto;
import com.app.studentgradetracker.model.Course;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements Mapper<CourseDto, Course>, RowMapper<Course> {

    private final ModelMapper modelMapper;

    public CourseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Course mapTo(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }

    @Override
    public CourseDto mapFrom(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Course.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .code(rs.getString("code"))
                .credits(rs.getInt("credits"))
                .department(rs.getString("department"))
                .build();
    }
}
