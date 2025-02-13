package pro.sky.deprtment;

import org.springframework.stereotype.Service;
import pro.sky.coursework.Employee;
import pro.sky.coursework.EmployeeNotFoundException;
import pro.sky.coursework.EmployeeService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Employee getEmployeeWithMaxSalary(int departmentId) {
        return employeeService.getAllEmployees().stream()
                .filter(employee -> employee.getDepartment() == departmentId)
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник не найден в отделе " + departmentId));
    }

    public Employee getEmployeeWithMinSalary(int departmentId) {
        return employeeService.getAllEmployees().stream()
                .filter(employee -> employee.getDepartment() == departmentId)
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник не найден в отделе " + departmentId));
    }

    public List<Employee> getAllEmployeesByDepartment(int departmentId) {
        return employeeService.getAllEmployees().stream()
                .filter(employee -> employee.getDepartment() == departmentId)
                .collect(Collectors.toList());
    }

    public Map<Integer, List<Employee>> getAllEmployeesGroupedByDepartment() {
        return employeeService.getAllEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }
}