package com.example.employeemanagement.service;

import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    public Page<Employee> getAllEmployees(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repository.findAll(pageable);
    }

    public Employee getEmployeeById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        return repository.findByDepartment(department);
    }

    public List<Employee> getEmployeesBySalaryRange(Double min, Double max) {
        return repository.findBySalaryBetween(min, max);
    }

    public List<Employee> searchEmployeeByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public Employee updateEmployee(Long id, Employee updated) {

        Employee emp = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        emp.setName(updated.getName());
        emp.setEmail(updated.getEmail());
        emp.setDepartment(updated.getDepartment());
        emp.setSalary(updated.getSalary());
        emp.setDesignation(updated.getDesignation());

        return repository.save(emp);
    }

    public void deleteEmployee(Long id) {

        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }

        repository.deleteById(id);
    }
}