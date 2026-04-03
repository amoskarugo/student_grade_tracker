package com.app.studentgradetracker.Dao;

import com.app.studentgradetracker.dto.StudentDto;
import com.app.studentgradetracker.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {

    Optional<Student> findById(Long id);
    Student create(Student student);
    List<Student> findAll();
    Student update(Student student, Long id);
    boolean delete(Long id);
}
