package com.app.studentgradetracker.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {


    @NotBlank(message = "name is required")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "must be a valid email")
    private String email;
}
