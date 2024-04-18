package com.ironbit.testironbit.document;

import com.ironbit.testironbit.model.EmployeeRequest;
import com.ironbit.testironbit.model.Gender;
import com.ironbit.testironbit.model.Position;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@Builder
@Document(collection = "employees")
public class Employee {

    private String id;

    @Field("first_name")
    private String firstName;

    @Field("second_name")
    private String secondName;

    @Field("last_name")
    private String lastName;

    @Field("second_last_name")
    private String secondLastName;

    private Long age;

    private Gender gender;

    @Field("birth_date")
    private LocalDate birthDate;

    private Position position;

    public static Employee toEmployeeDoc(final EmployeeRequest employeeRequest){
        return Employee.builder()
                .id(employeeRequest.getId())
                .firstName(employeeRequest.getFirstName())
                .secondName(employeeRequest.getSecondName())
                .lastName(employeeRequest.getLastName())
                .secondLastName(employeeRequest.getSecondLastName())
                .age(employeeRequest.getAge())
                .gender(employeeRequest.getGender())
                .birthDate(employeeRequest.getBirthDate())
                .position(employeeRequest.getPosition())
                .build();
    }
}
