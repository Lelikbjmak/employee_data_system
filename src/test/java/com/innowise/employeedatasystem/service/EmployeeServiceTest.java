package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.entity.Employee;
import com.innowise.employeedatasystem.exception.EmployeeIsNotFoundException;
import com.innowise.employeedatasystem.repo.EmployeeRepository;
import com.innowise.employeedatasystem.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    @Order(1)
    @DisplayName(value = "Context loads test")
    void test() {
        Assertions.assertNotNull(employeeRepository);
        Assertions.assertNotNull(employeeService);
    }

    @Test
    @Order(2)
    @DisplayName(value = "`Save & Edit` employee")
    void mustSaveEmployeeTest() {

        final String lastName = "Test";

        Employee employee = Employee.builder()
                .lastName(lastName)
                .build();

        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        Mockito.verify(employeeRepository, Mockito.times(1)).save(employee);
        Assertions.assertEquals(employee, savedEmployee);
    }

    @Test
    @Order(3)
    @DisplayName(value = "`Get` all Employees")
    void mustReturnAllEmployeesTest() {

        List<Employee> employees = List.of(
                Employee.builder().firstName("First").build(),
                Employee.builder().firstName("Second").build()
        );

        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> allEmployees = employeeService.getAllEmployees();

        Mockito.verify(employeeRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(allEmployees);
    }

    @Test
    @Order(4)
    @DisplayName(value = "`Delete` all Employees")
    void mustDeleteAllEmployeesTest() {

        List<Employee> employees = List.of(
                Employee.builder().firstName("First").build(),
                Employee.builder().firstName("Second").build()
        );

        Mockito.when(employeeRepository.saveAll(employees)).thenReturn(employees);

        List<Employee> savedEmployees = employeeRepository.saveAll(employees);
        Assertions.assertNotNull(savedEmployees);

        // Call deleteEmployees() to delete Employees
        employeeService.deleteEmployeeList(savedEmployees);

        List<Employee> allEmployeesAfterDelete = employeeService.getAllEmployees();
        Assertions.assertTrue(allEmployeesAfterDelete.isEmpty());

        Mockito.verify(employeeRepository, Mockito.times(1)).saveAll(employees);
        Mockito.verify(employeeRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Order(4)
    @DisplayName(value = "`Delete` Employee")
    void mustDeleteEmployeeTest() {

        Employee employee = Employee.builder().firstName("First").build();

        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employee);
        Assertions.assertNotNull(savedEmployee);

        // Call deleteEmployees() to delete Employees
        employeeService.deleteEmployee(savedEmployee);

        List<Employee> allEmployeesAfterDelete = employeeService.getAllEmployees();
        Assertions.assertTrue(allEmployeesAfterDelete.isEmpty());

        Mockito.verify(employeeRepository, Mockito.times(1)).save(employee);
        Mockito.verify(employeeRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Order(5)
    @DisplayName(value = "`Find` Employee by id")
    void mustReturnEmployeeByIdTest() {

        final long id = 1;
        Employee employee = Employee.builder()
                .id(1L)
                .build();

        Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.getEmployeeById(id);

        Mockito.verify(employeeRepository, Mockito.times(1)).findById(id);
        Assertions.assertEquals(employee, foundEmployee);
    }

    @Test
    @Order(6)
    @DisplayName(value = "`Find` Employee List by id List")
    void mustReturnEmployeeListByIdList() {

        List<Long> idList = List.of(1L, 2L);
        List<Employee> employeeList = List.of(new Employee(), new Employee());

        Mockito.when(employeeRepository.findAllById(idList)).thenReturn(employeeList);
        List<Employee> foundEmployeeList = employeeService.getEmployeeListByIdList(idList);

        Mockito.verify(employeeRepository, Mockito.times(1)).findAllById(idList);
        Assertions.assertEquals(employeeList, foundEmployeeList);
    }

    @Test
    @Order(7)
    @DisplayName(value = "`Find` Employee by id. Throw EmployeeIsNotFoundException")
    void mustTrowExceptionInFindEmployeeByIdTest() {

        final long id = 1;

        Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(EmployeeIsNotFoundException.class, () -> employeeService.getEmployeeById(id));
    }

    @Test
    @Order(8)
    @DisplayName(value = "`Find` Employee by username")
    void mustReturnEmployeeByUserUsernameTest() {

        final String username = "username";

        Employee employee = Employee.builder()
                .id(1L)
                .build();

        Mockito.when(employeeRepository.findByUser_username(username)).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.findEmployeeByUserUsername(username);

        Mockito.verify(employeeRepository, Mockito.times(1)).findByUser_username(username);
        Assertions.assertEquals(employee, foundEmployee);
    }

    @Test
    @Order(9)
    @DisplayName(value = "`Find` Employee by User username. Throw EmployeeIsNotFoundException")
    void mustTrowExceptionInEmployeeByUserUsernameTest() {

        final String username = "username";
        Mockito.when(employeeRepository.findByUser_username(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(EmployeeIsNotFoundException.class, () -> employeeService.findEmployeeByUserUsername(username));
    }

    @Test
    @Order(10)
    void mustReturnAllUsersAfterSave() {

    }
}
