package com.app.studentgradetracker.repository;


import com.app.studentgradetracker.Dao.CourseDao;
import com.app.studentgradetracker.Exception.DatabaseException;
import com.app.studentgradetracker.Exception.DuplicateResourceException;
import com.app.studentgradetracker.Mappers.impl.CourseMapper;
import com.app.studentgradetracker.Mappers.impl.StudentMapper;
import com.app.studentgradetracker.model.Course;
import com.app.studentgradetracker.model.SqlStaments.SqlQueries;
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
public class CourseRepo implements CourseDao {

    private final JdbcTemplate jdbcTemplate;
    private final CourseMapper courseMapper;

    @Override
    public List<Course> findAll() {
        try {
            return jdbcTemplate.query(SqlQueries.Course.FIND_ALL, courseMapper);
        } catch (DataAccessException e) {
            log.error("Error fetching courses: {}", e.getMessage());
            throw new DatabaseException("Failed to fetch courses", e);
        }
    }

    @Override
    public Optional<Course> findById(Long id) {
        try {
            List<Course> result = jdbcTemplate.query(
                    SqlQueries.Course.FIND_BY_ID, courseMapper, id);
            return result.stream().findFirst();
        } catch (DataAccessException e) {
            log.error("Error fetching course {}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to fetch course with id: " + id, e);
        }
    }

    @Override
    public List<Course> findByDepartment(String department) {
        try {
            return jdbcTemplate.query(
                    SqlQueries.Course.FIND_BY_DEPT, courseMapper, department);
        } catch (DataAccessException e) {
            log.error("Error fetching courses for dept {}: {}", department, e.getMessage());
            throw new DatabaseException("Failed to fetch courses for department: " + department, e);
        }
    }

    @Override
    public boolean existsByCode(String code) {
        try {
            Integer count = jdbcTemplate.queryForObject(
                    SqlQueries.Course.EXISTS_BY_CODE, Integer.class, code);
            return count != null && count > 0;
        } catch (DataAccessException e) {
            log.error("Error checking course code {}: {}", code, e.getMessage());
            throw new DatabaseException("Failed to check course code: " + code, e);
        }
    }

    @Override
    public Course save(Course course) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        SqlQueries.Course.INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, course.getName());
                ps.setString(2, course.getCode());
                ps.setInt(3,    course.getCredits());
                ps.setString(4, course.getDepartment());
                return ps;
            }, keyHolder);
            course.setId(((Number) keyHolder.getKeys().get("id")).longValue());
            return course;
        } catch (DuplicateKeyException e) {
            throw new DuplicateResourceException(
                    "Course already exists with code: " + course.getCode());
        } catch (DataAccessException e) {
            log.error("Error saving course: {}", e.getMessage());
            throw new DatabaseException("Failed to save course", e);
        }
    }

    @Override
    public int update(Course course) {
        try {
            return jdbcTemplate.update(SqlQueries.Course.UPDATE,
                    course.getName(), course.getCode(),
                    course.getCredits(), course.getDepartment(), course.getId());
        } catch (DuplicateKeyException e) {
            throw new DuplicateResourceException(
                    "Course code already in use: " + course.getCode());
        } catch (DataAccessException e) {
            log.error("Error updating course {}: {}", course.getId(), e.getMessage());
            throw new DatabaseException("Failed to update course", e);
        }
    }

    @Override
    public int deleteById(Long id) {
        try {
            return jdbcTemplate.update(SqlQueries.Course.DELETE, id);
        } catch (DataAccessException e) {
            log.error("Error deleting course {}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to delete course with id: " + id, e);
        }
    }
}