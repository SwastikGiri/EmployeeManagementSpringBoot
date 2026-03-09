package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createEmployee(employee));
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> getEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(service.getAllEmployees(page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEmployeeById(id));
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable String department) {

        return ResponseEntity.ok(service.getEmployeesByDepartment(department));
    }

    @GetMapping("/salary")
    public ResponseEntity<List<Employee>> getEmployeesBySalaryRange(
            @RequestParam Double min,
            @RequestParam Double max) {

        return ResponseEntity.ok(service.getEmployeesBySalaryRange(min, max));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployee(@RequestParam String name) {

        return ResponseEntity.ok(service.searchEmployeeByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employee) {

        return ResponseEntity.ok(service.updateEmployee(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {

        service.deleteEmployee(id);

        return ResponseEntity.ok("Employee deleted successfully");
    }

    @GetMapping("/test")
    public String testApi() {
        return "Employee API working";
    }
}