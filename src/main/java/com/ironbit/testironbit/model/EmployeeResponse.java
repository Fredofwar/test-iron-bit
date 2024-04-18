package com.ironbit.testironbit.model;

import com.ironbit.testironbit.document.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EmployeeResponse implements JsonModel {

    private String id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String secondLastName;
    private Long age;
    private Gender gender;
    private LocalDate birthDate;
    private Position position;

    public static EmployeeResponse toEmployeeResponse(final Employee employee){
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .secondName(employee.getSecondName())
                .lastName(employee.getLastName())
                .secondLastName(employee.getSecondLastName())
                .age(employee.getAge())
                .gender(employee.getGender())
                .birthDate(employee.getBirthDate())
                .position(employee.getPosition())
                .build();
    }

}
