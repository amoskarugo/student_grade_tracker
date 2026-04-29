package com.app.studentgradetracker.service;


import com.app.studentgradetracker.Dao.StudentDao;
import com.app.studentgradetracker.Exception.ConflictException;
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

    public StudentDto create(StudentDto dto) {
        if (studentDao.existsByEmail(dto.getEmail()))
            throw new ConflictException("Student already exists with email: " + dto.getEmail());

        Student saved = studentDao.create(studentMapper.mapTo(dto));
        log.info("Created student with id: {}", saved.getId());
        return studentMapper.mapFrom(saved);
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


    public StudentDto update(Long id, StudentDto dto) {
        Student existing = studentDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", id));

        if (!existing.getEmail().equals(dto.getEmail()) &&
                studentDao.existsByEmail(dto.getEmail()))
            throw new ConflictException("Email already in use: " + dto.getEmail());

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        studentDao.update(existing);
        log.info("Updated student with id: {}", id);
        return studentMapper.mapFrom(existing);
    }

    public void delete(Long id) {
        studentDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", id));
        studentDao.deleteById(id);
        log.info("Deleted student with id: {}", id);
    }

}
