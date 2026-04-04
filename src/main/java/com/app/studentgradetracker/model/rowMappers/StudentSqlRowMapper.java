package com.app.studentgradetracker.model.rowMappers;

import com.app.studentgradetracker.model.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;


@Configuration
public class StudentSqlRowMapper {

    @Bean
    public RowMapper<Student> studentRowMapper(){
        return (rs, rowNum) -> Student.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .enrolled_at(rs.getDate("enrolled_at").toLocalDate())
                .build();
    }
}
