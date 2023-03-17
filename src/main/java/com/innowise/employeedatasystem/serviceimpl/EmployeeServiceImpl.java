package com.innowise.employeedatasystem.serviceimpl;

import com.innowise.employeedatasystem.entity.Employee;
import com.innowise.employeedatasystem.repo.EmployeeRepository;
import com.innowise.employeedatasystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Optional<Employee> findEmployeeByUserUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
