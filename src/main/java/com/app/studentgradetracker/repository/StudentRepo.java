package com.app.studentgradetracker.repository;

import com.app.studentgradetracker.Dao.StudentDao;
import com.app.studentgradetracker.model.SqlStaments.SqlQueries;
import com.app.studentgradetracker.model.Student;
import com.app.studentgradetracker.model.rowMappers.StudentSqlRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudentRepo implements StudentDao {
    private final JdbcTemplate jdbcTemplate;
    private final StudentSqlRowMapper studentSqlRowMapper;

    // Handles ResultSet → Student entity (separate from StudentMapper)




    @Override
    public Optional<Student> findById(Long id) {

        List<Student> result = jdbcTemplate.query(SqlQueries.Student.FIND_BY_ID,
                studentSqlRowMapper.studentRowMapper(), id);

        return result.stream().findFirst();
    }

    @Override
    public Student create(Student student) {
        KeyHolder keyHolder = new GeneratedKeyHolder();


        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SqlQueries.Student.INSERT, Statement.RETURN_GENERATED_KEYS);
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
