package com.sample;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/")
    public Employee add(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @GetMapping("/{id}")
    public Optional<Employee> findById(@PathVariable("id") Long id) {
        return employeeService.findById(id);
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }
}
