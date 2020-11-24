package com.sample.unit;

import com.sample.*;
import com.sample.model.EmployeeRepo;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    private final EmployeeRepository employeeRepository = mock(EmployeeRepo.class);
    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final EmployeeService employeeService = new EmployeeService(employeeRepository, restTemplate);

    @Test
    void shouldBeAbleToSaveEmployeeSuccessfully() {
        Employee actualEmployee = Employee.builder().id(1L).name("srini").age(18).build();
        when(employeeRepository.save(actualEmployee)).thenReturn(actualEmployee);
        DepartmentResponse departmentResponse = DepartmentResponse.builder().id(101L).name("CSC").build();
        ReflectionTestUtils.setField(employeeService, "departmentServiceURL", "http://localhost:8081");
        when(restTemplate.getForEntity("http://localhost:8081/1", DepartmentResponse.class))
          .thenReturn(new ResponseEntity<>(departmentResponse, HttpStatus.OK));

        Employee employee = employeeService.saveEmployee(actualEmployee);

        ArgumentCaptor<Employee> argumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(argumentCaptor.capture());
        Employee expectedEmployee = argumentCaptor.getValue();
        assertThat(expectedEmployee.getName()).isEqualTo("srini");
        assertThat(expectedEmployee.getAge()).isEqualTo(18);
        assertThat(expectedEmployee.getDepartmentId()).isEqualTo(101);
        assertThat(employee.getName()).isEqualTo("srini");
        assertThat(employee.getAge()).isEqualTo(18);
        assertThat(employee.getDepartmentId()).isEqualTo(101);
    }

    @Test
    void shouldGetAllEmployeesFromRepository() {
        ArrayList<Employee> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(Employee.builder().id(1L).name("srini").age(18).build());
        when(employeeRepository.findAll()).thenReturn(expectedEmployees);

        Collection<Employee> actualEmployees = employeeService.findAllEmployees();

        assertThat(actualEmployees).isEqualTo(expectedEmployees);
    }

    @Test
    void shouldGetEmployeeFromRepository() {
        Employee expectedEmployee = Employee.builder().id(1L).name("srini").age(18).build();
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(expectedEmployee));

        Employee actualEmployee = employeeService.findByEmployeeId(1L);

        assertThat(actualEmployee).isEqualTo(expectedEmployee);
    }

}