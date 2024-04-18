package com.ironbit.testironbit.servicetest;

import com.ironbit.testironbit.document.Employee;
import com.ironbit.testironbit.exception.ApplicationException;
import com.ironbit.testironbit.model.*;
import com.ironbit.testironbit.repository.EmployeeRepository;
import com.ironbit.testironbit.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee director;
    private Employee manager;
    private Employee operations;

    @BeforeEach
    public void before(){
        director = Employee.builder()
                .id("123")
                .firstName("John")
                .secondName("Jack")
                .lastName("Doe")
                .secondLastName("Jackson")
                .age(28L)
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1995, 11, 5))
                .position(Position.DIRECTOR)
                .build();

        manager = Employee.builder()
                .id("456")
                .firstName("John")
                .secondName("Jack")
                .lastName("Doe")
                .secondLastName("Jackson")
                .age(28L)
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1995, 11, 5))
                .position(Position.MANAGER)
                .build();

        operations = Employee.builder()
                .id("789")
                .firstName("John")
                .secondName("Jack")
                .lastName("Doe")
                .secondLastName("Jackson")
                .age(28L)
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1995, 11, 5))
                .position(Position.OPERATIONS)
                .build();
    }

    @Test
    public void getEmployeesTest() {
        given(this.employeeRepository.findAll()).willReturn(List.of(director, manager, operations));
        List<EmployeeResponse> employees = this.employeeService.getEmployees();
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isGreaterThan(0);
        assertThat(employees.size()).isEqualTo(3);
    }

    @Test
    public void getEmployeesEmptyListTest() {
        given(this.employeeRepository.findAll()).willReturn(Collections.emptyList());
        List<EmployeeResponse> employees = this.employeeService.getEmployees();
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(0);
    }

    @Test
    public void getEmployeeTest() throws Exception {
        given(this.employeeRepository.findById(operations.getId())).willReturn(Optional.of(operations));
        EmployeeResponse employee = this.employeeService.getEmployee(operations.getId());
        assertThat(employee).isNotNull();
    }

    @Test
    public void getEmployeeNotFoundTest() throws Exception {
        given(this.employeeRepository.findById(operations.getId())).willReturn(Optional.empty());
        Assertions.assertThrows(ApplicationException.class, () -> this.employeeService.getEmployee(operations.getId()));
    }

    @Test
    public void createEmployeesTest(){
        List<EmployeeRequest> employeeRequests = new ArrayList<>();
        EmployeeRequest employeeRequest = this.getEmployeeRequest(manager);
        employeeRequests.add(employeeRequest);
        given(this.employeeRepository.save(manager)).willReturn(manager);
        List<EmployeeResponseDetail> employees = this.employeeService.createEmployees(employeeRequests);
        assertThat(employees).isNotNull();
        assertThat(employees.get(0).getStatus()).isEqualTo("CREATED");
        assertThat(employees.get(0).getMessage()).isEqualTo("Employee created");
    }

    @Test
    public void createEmployeeWithIncorrectDataTest(){
        List<EmployeeRequest> employeeRequests = new ArrayList<>();
        EmployeeRequest employeeRequest = this.getEmployeeRequest(operations);
        employeeRequest.setBirthDate(null);
        employeeRequest.setGender(null);
        employeeRequests.add(employeeRequest);
        given(this.employeeRepository.save(operations)).willReturn(operations);
        List<EmployeeResponseDetail> employees = this.employeeService.createEmployees(employeeRequests);
        assertThat(employees).isNotNull();
        assertThat(employees.get(0).getStatus()).isEqualTo("ERROR");
        assertThat(employees.get(0).getMessage()).isNotEmpty();
    }

    @Test
    public void updateEmployeeTest() {
        EmployeeRequest employeeRequest = this.getEmployeeRequest(director);
        given(this.employeeRepository.findById(director.getId())).willReturn(Optional.of(director));
        given(this.employeeRepository.save(director)).willReturn(director);
        EmployeeResponse employeeResponse = this.employeeService.updateEmployee(employeeRequest);
        assertThat(employeeResponse).isNotNull();
    }

    @Test
    public void updateNonExistingEmployeeTest(){
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setId(director.getId());
        given(this.employeeRepository.findById(director.getId())).willReturn(Optional.empty());
        Assertions.assertThrows(ApplicationException.class, () -> this.employeeService.updateEmployee(employeeRequest));
    }

    @Test
    public void deleteEmployeeTest(){
        given(this.employeeRepository.findById(manager.getId())).willReturn(Optional.of(manager));
        willDoNothing().given(this.employeeRepository).delete(manager);
        this.employeeService.deleteEmployee(manager.getId());
        verify(this.employeeRepository, times(1)).delete(manager);
    }

    @Test
    public void deleteNonExistingEmployeeTest(){
        given(this.employeeRepository.findById(manager.getId())).willReturn(Optional.empty());
        Assertions.assertThrows(ApplicationException.class, () -> this.employeeService.deleteEmployee(manager.getId()));
    }

    private EmployeeRequest getEmployeeRequest(Employee employee) {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setId(employee.getId());
        employeeRequest.setFirstName(employee.getFirstName());
        employeeRequest.setSecondName(employee.getSecondName());
        employeeRequest.setLastName(employee.getLastName());
        employeeRequest.setSecondLastName(employee.getSecondLastName());
        employeeRequest.setAge(employee.getAge());
        employeeRequest.setGender(employee.getGender());
        employeeRequest.setBirthDate(employee.getBirthDate());
        employeeRequest.setPosition(employee.getPosition());
        return employeeRequest;
    }

}
