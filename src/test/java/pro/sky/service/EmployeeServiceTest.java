package pro.sky.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.coursework.EmployeeAlreadyAddedException;
import pro.sky.coursework.EmployeeNotFoundException;
import pro.sky.coursework.EmployeeStorageIsFullException;
import pro.sky.model.Employee;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();
    }

    @ParameterizedTest
    @CsvSource({
            "John, Doe, 50000, 1",
            "Jane, Smith, 60000, 2",
            "Alice, Johnson, 55000, 3"
    })
    void addEmployee_ShouldAddEmployee(String firstName, String lastName, int salary, int department) {
        Employee employee = employeeService.addEmployee(firstName, lastName, salary, department);
        assertNotNull(employee);
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(salary, employee.getSalary());
        assertEquals(department, employee.getDepartment());
    }

    @ParameterizedTest
    @CsvSource({
            "John, Doe",
            "Jane, Smith",
            "Alice, Johnson"
    })
    void removeEmployee_ShouldRemoveEmployee(String firstName, String lastName) {
        employeeService.addEmployee(firstName, lastName, 50000, 1);
        Employee removedEmployee = employeeService.removeEmployee(firstName, lastName);
        assertNotNull(removedEmployee);
        assertEquals(firstName, removedEmployee.getFirstName());
        assertEquals(lastName, removedEmployee.getLastName());
    }

    @ParameterizedTest
    @CsvSource({
            "John, Doe",
            "Jane, Smith",
            "Alice, Johnson"
    })
    void findEmployee_ShouldFindEmployee(String firstName, String lastName) {
        employeeService.addEmployee(firstName, lastName, 50000, 1);
        Employee foundEmployee = employeeService.findEmployee(firstName, lastName);
        assertNotNull(foundEmployee);
        assertEquals(firstName, foundEmployee.getFirstName());
        assertEquals(lastName, foundEmployee.getLastName());
    }

    @ParameterizedTest
    @MethodSource("provideEmployeesForStorageFullTest")
    void addEmployee_ShouldThrowEmployeeStorageIsFullException(String firstName, String lastName, int salary, int department) {
        for (int i = 0; i < 10; i++) {
            employeeService.addEmployee("Employee" + i, "LastName" + i, 50000, 1);
        }
        assertThrows(EmployeeStorageIsFullException.class, () -> {
            employeeService.addEmployee(firstName, lastName, salary, department);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "John, Doe",
            "Jane, Smith",
            "Alice, Johnson"
    })
    void removeEmployee_ShouldThrowEmployeeNotFoundException(String firstName, String lastName) {
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.removeEmployee(firstName, lastName);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "John, Doe",
            "Jane, Smith",
            "Alice, Johnson"
    })
    void findEmployee_ShouldThrowEmployeeNotFoundException(String firstName, String lastName) {
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.findEmployee(firstName, lastName);
        });
    }

    @Test
    void getAllEmployees_ShouldReturnAllEmployees() {
        employeeService.addEmployee("John", "Doe", 50000, 1);
        employeeService.addEmployee("Jane", "Smith", 60000, 2);
        List<Employee> employees = employeeService.getAllEmployees();
        assertEquals(2, employees.size());
    }

    private static Stream<Arguments> provideEmployeesForStorageFullTest() {
        return Stream.of(
                Arguments.of("John", "Doe", 50000, 1),
                Arguments.of("Jane", "Smith", 60000, 2),
                Arguments.of("Alice", "Johnson", 55000, 3)
        );
    }
}