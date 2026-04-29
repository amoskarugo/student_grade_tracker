package com.app.studentgradetracker.service;


import com.app.studentgradetracker.Dao.CourseDao;
import com.app.studentgradetracker.Exception.ConflictException;
import com.app.studentgradetracker.Exception.ResourceNotFoundException;
import com.app.studentgradetracker.Mappers.impl.CourseMapper;
import com.app.studentgradetracker.dto.CourseDto;
import com.app.studentgradetracker.model.Course;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CourseService {

    private final CourseDao courseDao;
    private final CourseMapper courseMapper;

    @Transactional(readOnly = true)
    public List<CourseDto> findAll() {
        return courseDao.findAll().stream()
                .map(courseMapper::mapFrom).toList();
    }

    @Transactional(readOnly = true)
    public CourseDto findById(Long id) {
        return courseDao.findById(id)
                .map(courseMapper::mapFrom)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id));
    }

    @Transactional(readOnly = true)
    public List<CourseDto> findByDepartment(String department) {
        return courseDao.findByDepartment(department).stream()
                .map(courseMapper::mapFrom).toList();
    }

    public CourseDto create(CourseDto dto) {
        if (courseDao.existsByCode(dto.getCode()))
            throw new ConflictException("Course already exists with code: " + dto.getCode());

        Course saved = courseDao.save(courseMapper.mapTo(dto));
        log.info("Created course with id: {}", saved.getId());
        return courseMapper.mapFrom(saved);
    }

    public CourseDto update(Long id, CourseDto dto) {
        Course existing = courseDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id));

        if (!existing.getCode().equals(dto.getCode()) &&
                courseDao.existsByCode(dto.getCode()))
            throw new ConflictException("Course code already in use: " + dto.getCode());

        existing.setName(dto.getName());
        existing.setCode(dto.getCode());
        existing.setCredits(dto.getCredits());
        existing.setDepartment(dto.getDepartment());
        courseDao.update(existing);
        log.info("Updated course with id: {}", id);
        return courseMapper.mapFrom(existing);
    }

    public void delete(Long id) {
        courseDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id));
        courseDao.deleteById(id);
        log.info("Deleted course with id: {}", id);
    }
}