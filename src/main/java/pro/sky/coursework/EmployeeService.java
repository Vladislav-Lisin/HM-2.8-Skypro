package pro.sky.coursework;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private static final int maxCountEmployees = 10;
    List<Employee> employeesList = new ArrayList<>();


    public Employee addEmployee(String firstName, String lastName, int salary, int department) {
        Employee employee = new Employee(firstName, lastName, salary, department);
        if (employeesList.size() >= maxCountEmployees) {
            throw new EmployeeStorageIsFullException("Достигнут лимит количества сотрудников в фирме.");
        }
        if (employeesList.contains(employee)) {
            throw new EmployeeAlreadyAddedException("Сотрудник с таким именем уже существует.");
        }
        employeesList.add(employee);
        return employee;
    }

    public Employee removeEmployee(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName, 0, 0); // Salary and department are not used for comparison
        if (!employeesList.contains(employee)) {
            throw new EmployeeNotFoundException("Сотрудник с таким именем не найден.");
        }
        employeesList.remove(employee);
        return employee;
    }

    public Employee findEmployee(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName, 0, 0); // Salary and department are not used for comparison
        if (!employeesList.contains(employee)) {
            throw new EmployeeNotFoundException("Сотрудник не найден.");
        }
        return employee;
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeesList);
    }

}
