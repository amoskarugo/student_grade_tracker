package com.app.studentgradetracker.Dao;


import com.app.studentgradetracker.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {

    Optional<Student> findById(Long id);
    Student create(Student student);
    List<Student> findAll();
    int update(Student student, Long id);
    int delete(Long id);
    Optional<Student> findByEmail(String email);
    boolean           existsByEmail(String email);
}
