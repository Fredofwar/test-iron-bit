package com.ironbit.testironbit.service;

import com.ironbit.testironbit.document.Employee;
import com.ironbit.testironbit.exception.ApplicationException;
import com.ironbit.testironbit.model.EmployeeResponseDetail;
import com.ironbit.testironbit.model.EmployeeRequest;
import com.ironbit.testironbit.model.EmployeeResponse;
import com.ironbit.testironbit.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeResponse> getEmployees(){
        List<Employee> employees = this.employeeRepository.findAll();
        return employees.stream().map(EmployeeResponse::toEmployeeResponse).toList();
    }

    public EmployeeResponse getEmployee(final String id) throws Exception {
        Optional<Employee> employee = this.employeeRepository.findById(id);
        if(!employee.isPresent()){
            throw new ApplicationException("Get Error", String.format("Employee %s not found", id), HttpStatus.BAD_REQUEST);
        }
        return EmployeeResponse.toEmployeeResponse(employee.get());
    }

    public EmployeeResponse updateEmployee(@RequestBody final EmployeeRequest employeeRequest){
        Optional<Employee> employee = this.employeeRepository.findById(employeeRequest.getId());
        if(!employee.isPresent()){
            throw new ApplicationException("Update Error", String.format("Employee %s not exist", employeeRequest.getId()), HttpStatus.BAD_REQUEST);
        }
        this.employeeRepository.save(Employee.toEmployeeDoc(employeeRequest));
        return this.buildResponse(employeeRequest);
    }

    public List<EmployeeResponseDetail> createEmployees(final List<EmployeeRequest> request){
        List<EmployeeResponseDetail> employeesCreated = new ArrayList<>();
        request.forEach(req -> {
            EmployeeResponseDetail createEmployeeResponse = this.buildResponse(req);
            try {
                Employee employee = Employee.toEmployeeDoc(req);
                this.employeeRepository.save(employee);
                createEmployeeResponse.setStatus("CREATED");
                createEmployeeResponse.setMessage("Employee created");
            } catch (Exception e){
                createEmployeeResponse.setStatus("ERROR");
                createEmployeeResponse.setMessage(e.getMessage());
            }
            employeesCreated.add(createEmployeeResponse);
        });
        return employeesCreated;
    }

    public void deleteEmployee(final String id){
        Optional<Employee> employee = this.employeeRepository.findById(id);
        if(!employee.isPresent()){
            throw new ApplicationException("Delete Error", String.format("Employee %s not found", id), HttpStatus.BAD_REQUEST);
        }
        this.employeeRepository.delete(employee.get());
    }

    private EmployeeResponseDetail buildResponse(final EmployeeRequest request){
        return new EmployeeResponseDetail(request.getId(), request.getFirstName(), request.getSecondName(),
                request.getLastName(), request.getSecondLastName(), request.getAge(), request.getGender(), request.getBirthDate(),
                request.getPosition());
    }

}
