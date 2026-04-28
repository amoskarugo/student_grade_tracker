package com.app.studentgradetracker.service;


import com.app.studentgradetracker.Dao.StudentDao;
import com.app.studentgradetracker.Exception.ResourceNotFoundException;
import com.app.studentgradetracker.Mappers.impl.StudentMapper;
import com.app.studentgradetracker.dto.StudentDto;
import com.app.studentgradetracker.model.Student;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudentService {
    private final StudentMapper studentMapper;
    private final StudentDao studentDao;


//    public StudentDto
    public Student save(Student student){
        return studentDao.create(student);
    }

    public List<StudentDto> findAll(){
        return studentDao.findAll().stream().map(studentMapper::mapFrom).toList();
    }

    @Transactional(readOnly = true)
    public StudentDto findById(Long id) {
        return studentDao.findById(id)
                .map(studentMapper::mapFrom)
                .orElseThrow(() -> new ResourceNotFoundException("Student", id));
    }

}
