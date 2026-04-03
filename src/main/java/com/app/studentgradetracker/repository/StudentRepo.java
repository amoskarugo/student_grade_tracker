package com.app.studentgradetracker.repository;

import com.app.studentgradetracker.Dao.StudentDao;
import com.app.studentgradetracker.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
    public Student create(Student student) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO students (name, email) VALUES (?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            return ps;
        }, keyHolder);

        student.setId(                            // 3. set the ID on the student object
                ((Number) keyHolder.getKeys().get("id")).longValue()
        );

        return student;
    }

    @Override
    public List<Student> findAll() {
        return List.of();
    }

    @Override
    public Student update(Student student, Long id) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
