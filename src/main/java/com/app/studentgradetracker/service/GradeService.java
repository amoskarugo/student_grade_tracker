package com.app.studentgradetracker.service;

import com.app.studentgradetracker.Dao.CourseDao;
import com.app.studentgradetracker.Dao.GradeDao;
import com.app.studentgradetracker.Dao.StudentDao;
import com.app.studentgradetracker.Exception.ConflictException;
import com.app.studentgradetracker.Exception.ResourceNotFoundException;
import com.app.studentgradetracker.Mappers.impl.GradeMapper;
import com.app.studentgradetracker.dto.GpaResult;
import com.app.studentgradetracker.dto.GradeDto;
import com.app.studentgradetracker.model.Grade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GradeService {

    private final GradeDao gradeDao;
    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final GradeMapper gradeMapper;

    public GradeDto recordGrade(GradeDto dto) {
        studentDao.findById(dto.getStudent_id())
                .orElseThrow(() -> new ResourceNotFoundException("Student", dto.getStudent_id()));

        courseDao.findById(dto.getCourse_id())
                .orElseThrow(() -> new ResourceNotFoundException("Course", dto.getCourse_id()));

        if (gradeDao.exists(dto.getStudent_id(), dto.getCourse_id(), dto.getSemester()))
            throw new ConflictException(
                    "Grade already recorded for this student in this course and semester");

        Grade saved = gradeDao.save(gradeMapper.mapTo(dto));
        log.info("Recorded grade for student {} course {}", dto.getStudent_id(), dto.getCourse_id());
        return gradeMapper.mapFrom(saved);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getGradesWithDetails(Long studentId) {
        studentDao.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", studentId));
        return gradeDao.findWithDetails(studentId);
    }

    @Transactional(readOnly = true)
    public List<GpaResult> getGpa(Long studentId) {
        studentDao.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", studentId));

        return gradeDao.calculateGpa(studentId).stream()
                .map(row -> GpaResult.builder()
                        .studentId(studentId)
                        .studentName((String) row.get("student_name"))
                        .semester((String) row.get("semester"))
                        .gpa(((Number) row.get("gpa")).doubleValue())
                        .totalCourses(((Number) row.get("total_courses")).intValue())
                        .build())
                .toList();
    }

    public void deleteGrade(Long id) {
        if (gradeDao.deleteById(id) == 0)
            throw new ResourceNotFoundException("Grade", id);
        log.info("Deleted grade with id: {}", id);
    }
}
