package com.app.studentgradetracker.Dao;

import com.app.studentgradetracker.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao {

    List<Course>     findAll();
    Optional<Course> findById(Long id);
    List<Course> findByDepartment(String department);
    boolean          existsByCode(String code);
    Course           save(Course course);
    int              update(Course course);
    int              deleteById(Long id);
}
