package com.ironbit.testironbit.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeResponseDetail extends EmployeeResponse implements JsonModel{

    private String status;
    private String message;

    public EmployeeResponseDetail(String id, String firstName, String secondName, String lastName, String secondLastName, Long age, Gender gender, LocalDate birthDate, Position position) {
        super(id, firstName, secondName, lastName, secondLastName, age, gender, birthDate, position);
    }
}
