package pro.sky.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.coursework.EmployeeNotFoundException;
import pro.sky.model.Employee;
import pro.sky.service.DepartmentService;
import pro.sky.service.EmployeeService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    private List<Employee> employees;

    @BeforeEach
    void setUp() {
        employees = List.of(
                new Employee("John", "Doe", 5000, 1),
                new Employee("Jane", "Smith", 6000, 1),
                new Employee("Alice", "Johnson", 7000, 2),
                new Employee("Bob", "Brown", 4500, 2),
                new Employee("Charlie", "Davis", 5500, 3)
        );
    }

    @Test
    void testGetEmployeeWithMaxSalary() {
        when(employeeService.getAllEmployees()).thenReturn(employees);

        Employee result = departmentService.getEmployeeWithMaxSalary(1);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals(6000, result.getSalary());
        assertEquals(1, result.getDepartment());
    }

    @Test
    void testGetEmployeeWithMaxSalaryWhenDepartmentNotFound() {
        when(employeeService.getAllEmployees()).thenReturn(employees);

        assertThrows(EmployeeNotFoundException.class, () -> {
            departmentService.getEmployeeWithMaxSalary(4);
        });
    }

    @Test
    void testGetEmployeeWithMinSalary() {
        when(employeeService.getAllEmployees()).thenReturn(employees);

        Employee result = departmentService.getEmployeeWithMinSalary(2);

        assertNotNull(result);
        assertEquals("Bob", result.getFirstName());
        assertEquals("Brown", result.getLastName());
        assertEquals(4500, result.getSalary());
        assertEquals(2, result.getDepartment());
    }

    @Test
    void testGetEmployeeWithMinSalaryWhenDepartmentNotFound() {
        when(employeeService.getAllEmployees()).thenReturn(employees);

        assertThrows(EmployeeNotFoundException.class, () -> {
            departmentService.getEmployeeWithMinSalary(4);
        });
    }

    @Test
    void testGetAllEmployeesByDepartment() {
        when(employeeService.getAllEmployees()).thenReturn(employees);

        List<Employee> result = departmentService.getAllEmployeesByDepartment(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(employee -> employee.getDepartment() == 1));
    }

    @Test
    void testGetAllEmployeesByDepartmentWhenDepartmentNotFound() {
        when(employeeService.getAllEmployees()).thenReturn(employees);

        List<Employee> result = departmentService.getAllEmployeesByDepartment(4);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllEmployeesByAllDepartments() {
        when(employeeService.getAllEmployees()).thenReturn(employees);

        Map<Integer, List<Employee>> result = departmentService.getAllEmployeesByAllDepartments();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.containsKey(1));
        assertTrue(result.containsKey(2));
        assertTrue(result.containsKey(3));
        assertEquals(2, result.get(1).size());
        assertEquals(2, result.get(2).size());
        assertEquals(1, result.get(3).size());
    }
}