package com.app.studentgradetracker.repository;

import com.app.studentgradetracker.Dao.GradeDao;
import com.app.studentgradetracker.Exception.DatabaseException;
import com.app.studentgradetracker.Exception.DuplicateResourceException;
import com.app.studentgradetracker.Mappers.impl.GradeMapper;
import com.app.studentgradetracker.model.Grade;
import com.app.studentgradetracker.constants.SqlQueries;
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
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GradeRepo implements GradeDao {

    private final JdbcTemplate jdbc;
    private final GradeMapper gradeMapper;

    @Override
    public List<Grade> findByStudentId(Long studentId) {
        try {
            return jdbc.query(
                    SqlQueries.Grade.FIND_BY_STUDENT, gradeMapper, studentId);
        } catch (DataAccessException e) {
            log.error("Error fetching grades for student {}: {}", studentId, e.getMessage());
            throw new DatabaseException("Failed to fetch grades for student: " + studentId, e);
        }
    }

    @Override
    public List<Grade> findByStudentAndSemester(Long studentId, String semester) {
        try {
            return jdbc.query(
                    SqlQueries.Grade.FIND_BY_SEMESTER, gradeMapper, studentId, semester);
        } catch (DataAccessException e) {
            log.error("Error fetching grades: {}", e.getMessage());
            throw new DatabaseException("Failed to fetch grades", e);
        }
    }

    @Override
    public boolean exists(Long studentId, Long courseId, String semester) {
        try {
            Integer count = jdbc.queryForObject(
                    SqlQueries.Grade.EXISTS, Integer.class, studentId, courseId, semester);
            return count != null && count > 0;
        } catch (DataAccessException e) {
            log.error("Error checking grade existence: {}", e.getMessage());
            throw new DatabaseException("Failed to check grade existence", e);
        }
    }

    @Override
    public Grade save(Grade grade) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        SqlQueries.Grade.INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1,   grade.getStudent_id());
                ps.setLong(2,   grade.getCourse_id());
                ps.setDouble(3, grade.getGrade());
                ps.setString(4, grade.getSemester());
                return ps;
            }, keyHolder);
            grade.setId(((Number) keyHolder.getKeys().get("id")).longValue());
            return grade;
        } catch (DuplicateKeyException e) {
            throw new DuplicateResourceException(
                    "Grade already recorded for this student in this course and semester");
        } catch (DataAccessException e) {
            log.error("Error saving grade: {}", e.getMessage());
            throw new DatabaseException("Failed to save grade", e);
        }
    }

    @Override
    public int deleteById(Long id) {
        try {
            return jdbc.update(SqlQueries.Grade.DELETE, id);
        } catch (DataAccessException e) {
            log.error("Error deleting grade {}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to delete grade with id: " + id, e);
        }
    }

    @Override
    public List<Map<String, Object>> findWithDetails(Long studentId) {
        try {
            return jdbc.queryForList(SqlQueries.Grade.FIND_WITH_DETAILS, studentId);
        } catch (DataAccessException e) {
            log.error("Error fetching grade details for student {}: {}", studentId, e.getMessage());
            throw new DatabaseException("Failed to fetch grade details", e);
        }
    }

    @Override
    public List<Map<String, Object>> calculateGpa(Long studentId) {
        try {
            return jdbc.queryForList(SqlQueries.Grade.CALCULATE_GPA, studentId);
        } catch (DataAccessException e) {
            log.error("Error calculating GPA for student {}: {}", studentId, e.getMessage());
            throw new DatabaseException("Failed to calculate GPA", e);
        }
    }
}