package com.sample;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeControllerTest {

    private EmployeeService employeeService = mock(EmployeeService.class);
    private EmployeeController employeeController = new EmployeeController(employeeService);

    @Test
    void shouldCallServiceToGetAllEmployees() {
        List<Employee> expectedEmployees = singletonList(new Employee(1L, 101L, "srini", 18));
        when(employeeService.findAll()).thenReturn(expectedEmployees);

        Collection<Employee> actualEmployees = employeeController.findAll();

        assertThat(actualEmployees).isEqualTo(expectedEmployees);
    }

    @Test
    void shouldCallServiceToGetEmployeeById() {
        Employee expectedEmployee = new Employee(1L, 101L, "srini", 18);
        when(employeeService.findById(1L)).thenReturn(Optional.of(expectedEmployee));

        Optional<Employee> actualEmployee = employeeController.findById(1L);

        assertThat(actualEmployee).isEqualTo(Optional.of(expectedEmployee));
    }

    @Test
    void shouldCallServiceToSaveEmployee() {
        Employee actualEmployee = new Employee(1L, 101L, "srini", 18);
        when(employeeService.save(actualEmployee)).thenReturn(actualEmployee);

        Employee expectedEmployee = employeeController.add(actualEmployee);

        assertThat(actualEmployee).isSameAs(expectedEmployee);
    }
}