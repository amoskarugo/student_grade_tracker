package com.app.studentgradetracker.model;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Grade {

    private Long id;
    private Long student_id;
    private Long course_id;
    private double grade;
    private  String semester;
    private LocalDateTime created_at;
}
