package com.ironbit.testironbit.controller;

import com.ironbit.testironbit.model.EmployeeRequest;
import com.ironbit.testironbit.model.EmployeeResponse;
import com.ironbit.testironbit.model.EmployeeResponseDetail;
import com.ironbit.testironbit.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Employees", description = "Rest API for employee management")
@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getEmployees(HttpServletRequest req){
        log.info("HEADER > " + req.getHeader("Accept"));
        List<EmployeeResponse> employees = this.employeeService.getEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable("id") final String id, HttpServletRequest req) throws Exception {
        log.info("HEADER > " + req.getHeader("User-Agent"));
        EmployeeResponse employee = this.employeeService.getEmployee(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<EmployeeResponseDetail>> createEmployees(@RequestBody final List<EmployeeRequest> request){
        List<EmployeeResponseDetail> employees = this.employeeService.createEmployees(request);
        return new ResponseEntity<>(employees, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<EmployeeResponse> updateEmployee(@RequestBody final EmployeeRequest employeeRequest){
        EmployeeResponse employeeResponse = this.employeeService.updateEmployee(employeeRequest);
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") final String id){
        this.employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

