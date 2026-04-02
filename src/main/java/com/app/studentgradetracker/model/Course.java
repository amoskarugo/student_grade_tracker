package com.app.studentgradetracker.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Course {
    private Long id;
    private String name;
    private String code;
    private int credits;
    private String department;
}
