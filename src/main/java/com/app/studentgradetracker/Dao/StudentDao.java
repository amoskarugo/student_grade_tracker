package com.app.studentgradetracker.Dao;

import com.app.studentgradetracker.dto.StudentDto;
import com.app.studentgradetracker.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {

    Optional<Student> findById(Long id);
    StudentDto createStudent(StudentDto studentDto);
    List<StudentDto> selectAll();
    StudentDto update(StudentDto studentDto, Long id);
}
