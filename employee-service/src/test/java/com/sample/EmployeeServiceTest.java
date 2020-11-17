package com.sample;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    private final EmployeeRepository employeeRepository = mock(EmployeeRepo.class);
    private final EmployeeService employeeService = new EmployeeService(employeeRepository);

    @Test
    void shouldCallDaoToSaveEmployee() {
        Employee actualEmployee = new Employee(1L, 101L, "srini", 18);
        when(employeeRepository.save(actualEmployee)).thenReturn(actualEmployee);

        Employee employee = employeeService.save(actualEmployee);

        ArgumentCaptor<Employee> argumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(argumentCaptor.capture());
        Employee expectedEmployee = argumentCaptor.getValue();
        assertThat(expectedEmployee.getName()).isEqualTo("srini");
        assertThat(expectedEmployee.getAge()).isEqualTo(18);
        assertThat(employee.getName()).isEqualTo("srini");
        assertThat(employee.getAge()).isEqualTo(18);
    }

    @Test
    void shouldGetAllEmployeesFromRepository() {
        ArrayList<Employee> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(new Employee(1L, 101L, "srini", 18));
        when(employeeRepository.findAll()).thenReturn(expectedEmployees);

        Collection<Employee> actualEmployees = employeeService.findAll();

        assertThat(actualEmployees).isEqualTo(expectedEmployees);
    }

    @Test
    void shouldGetEmployeeFromRepository() {
        Employee expectedEmployee = new Employee(1L, 101L, "srini", 18);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(expectedEmployee));

        Optional<Employee> actualEmployee = employeeService.findById(1L);

        assertThat(actualEmployee).isEqualTo(Optional.of(expectedEmployee));
    }

}