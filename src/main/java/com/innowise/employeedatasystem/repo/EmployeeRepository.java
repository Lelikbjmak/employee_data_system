package com.innowise.employeedatasystem.repo;

import com.innowise.employeedatasystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
