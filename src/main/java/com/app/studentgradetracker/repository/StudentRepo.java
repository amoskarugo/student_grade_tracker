package com.app.studentgradetracker.repository;

import com.app.studentgradetracker.Dao.StudentDao;
import com.app.studentgradetracker.Exception.DatabaseException;
import com.app.studentgradetracker.Exception.DuplicateResourceException;
import com.app.studentgradetracker.Mappers.impl.StudentMapper;
import com.app.studentgradetracker.constants.SqlQueries;
import com.app.studentgradetracker.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
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
@Slf4j
public class StudentRepo implements StudentDao {
    private final JdbcTemplate jdbcTemplate;
    private final StudentMapper studentMapper;


    @Override
    public Optional<Student> findById(Long id) {
        try {

            List<Student> result = jdbcTemplate.query(SqlQueries.Student.FIND_BY_ID,
                    studentMapper, id);
            return result.stream().findFirst();
        }catch (DataAccessException e){
            log.error("Error fetching student {}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to fetch student with id: " + id, e);
        }
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
        try{
            return jdbcTemplate.query(SqlQueries.Student.FIND_ALL, studentMapper);
        }catch (DataAccessException e){
            log.error("Error fetching all students: {}", e.getMessage());
            throw new DatabaseException("Failed to fetch students", e);
        }
    }

    @Override
    public void update(Student student) {
        try {
            jdbcTemplate.update(SqlQueries.Student.UPDATE,
                    student.getName(), student.getEmail(), student.getId());
        } catch (DuplicateKeyException e) {
            throw new DuplicateResourceException("Email already in use: " + student.getEmail());
        } catch (DataAccessException e) {
            log.error("Error updating student {}: {}", student.getId(), e.getMessage());
            throw new DatabaseException("Failed to update student", e);
        }
    }

    @Override
    public int delete(Long id) {
        try {
            return jdbcTemplate.update(SqlQueries.Student.DELETE_BY_ID, id);
        } catch (DataAccessException e) {
            log.error("Error deleting student {}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to delete student with id: " + id, e);
        }
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        try{

            List<Student> result =
                    jdbcTemplate.query(SqlQueries.Student.FIND_BY_EMAIL, studentMapper, email);
            return result.stream().findFirst();

        } catch (DataAccessException e) {
            log.error("Error fetching student by email {}: {}", email, e.getMessage());
            throw new DatabaseException("Failed to fetch student with email: " + email, e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try {
            Integer count = jdbcTemplate.queryForObject(
                    SqlQueries.Student.EXISTS_BY_EMAIL, Integer.class, email);
            return count != null && count > 0;
        } catch (DataAccessException e) {
            log.error("Error checking email {}: {}", email, e.getMessage());
            throw new DatabaseException("Failed to check email: " + email, e);
        }
    }

    @Override
    public int deleteById(Long id) {
        try {
            return jdbcTemplate.update(SqlQueries.Student.DELETE_BY_ID, id);
        } catch (DataAccessException e) {
            log.error("Error deleting student {}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to delete student with id: " + id, e);
        }
    }
}
