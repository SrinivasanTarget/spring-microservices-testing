package com.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

import static org.springframework.web.util.UriComponentsBuilder.*;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Value("${departmentservice.baseurl}")
    private String departmentServiceURL;

    private final RestTemplate restTemplate;

    public EmployeeService(EmployeeRepository employeeRepository, RestTemplate restTemplate) {
        this.employeeRepository = employeeRepository;
        this.restTemplate = restTemplate;
    }

    public Employee saveEmployee(Employee employee) {
        String uri = fromHttpUrl(departmentServiceURL)
                .path("/" + employee.getId())
                .toUriString();

        ResponseEntity<DepartmentResponse> responseEntity = restTemplate.getForEntity(uri, DepartmentResponse.class);

        employee.setDepartmentId(Objects.requireNonNull(responseEntity.getBody()).getId());
        return employeeRepository.save(employee);
    }

    public Employee findByEmployeeId(Long id) {
        return employeeRepository.findById(id).get();
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }
}
