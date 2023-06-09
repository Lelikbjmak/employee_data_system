package com.innowise.employeedatasystem.repo;

import com.innowise.employeedatasystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByFirstNameAndLastNameAndMiddleNameAndHireDate(String firstName, String lastName, String middleName, Date hireDate);

    Optional<Employee> findByUser_username(String username);

    List<Employee> findAllByUser_usernameIn(List<String> usernameList);
}
