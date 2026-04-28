package com.app.studentgradetracker.Dao;

import com.app.studentgradetracker.model.Grade;

import java.util.List;
import java.util.Map;

public interface GradeDao {

    List<Grade> findByStudentId(Long studentId);
    List<Grade>               findByStudentAndSemester(Long studentId, String semester);
    boolean                   exists(Long studentId, Long courseId, String semester);
    Grade                     save(Grade grade);
    int                       deleteById(Long id);
    List<Map<String, Object>> findWithDetails(Long studentId);
    List<Map<String, Object>> calculateGpa(Long studentId);
}
