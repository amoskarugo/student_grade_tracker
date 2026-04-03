package com.app.studentgradetracker.model;


import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Student {
    private Long id;
    private String name;
    private String email;
    private LocalDate enrolled_at;
}
