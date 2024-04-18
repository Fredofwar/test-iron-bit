package com.ironbit.testironbit.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequest implements JsonModel {

    private String id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String secondLastName;
    private Long age;
    private Gender gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    private Position position;

}
