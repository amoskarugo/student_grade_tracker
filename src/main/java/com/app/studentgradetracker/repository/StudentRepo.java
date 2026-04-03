package com.app.studentgradetracker.repository;


import com.app.studentgradetracker.Dao.StudentDao;
import com.app.studentgradetracker.Mappers.impl.StudentMapper;
import com.app.studentgradetracker.dto.StudentDto;
import com.app.studentgradetracker.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepo implements StudentDao {
    private final JdbcTemplate jdbcTemplate;
    public StudentRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Handles ResultSet → Student entity (separate from StudentMapper)
    private final RowMapper<Student> studentRowMapper = (rs, rowNum) -> Student.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .email(rs.getString("email"))
            .enrolled_at(rs.getDate("enrolled_at").toLocalDate())
            .build();

    @Override
    public Optional<Student> findById(Long id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        List<Student> result = jdbcTemplate.query(sql, studentRowMapper, id);

        return result.stream().findFirst();
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        return null;
    }

    @Override
    public List<StudentDto> selectAll() {
        return List.of();
    }

    @Override
    public StudentDto update(StudentDto studentDto, Long id) {
        return null;
    }
}
