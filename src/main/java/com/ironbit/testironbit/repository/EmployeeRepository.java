package com.ironbit.testironbit.repository;

import com.ironbit.testironbit.document.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

}
