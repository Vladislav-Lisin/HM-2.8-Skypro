package pro.sky.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.model.Employee;
import pro.sky.service.DepartmentService;


@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/employees")
    public Object getAllEmployees() {
        return departmentService.getAllEmployeesByAllDepartments();
    }

    @GetMapping("/{id}/salary/max")
    public Employee getEmployeeWithMaxSalary(@PathVariable int id) {
        return departmentService.getEmployeeWithMaxSalary(id);
    }

    @GetMapping("/{id}/salary/min")
    public Employee getEmployeeWithMinSalary(@PathVariable int id) {
        return departmentService.getEmployeeWithMinSalary(id);
    }

    @GetMapping("/{id}/employees")
    public Object getAllEmployeesByDepartment(@PathVariable Integer id) {
        return departmentService.getAllEmployeesByDepartment(id);
    }




}