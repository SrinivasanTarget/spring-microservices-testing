package com.sample.unit;

import com.sample.Employee;
import com.sample.EmployeeController;
import com.sample.EmployeeService;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeControllerTest {

    private final EmployeeService employeeService = mock(EmployeeService.class);
    private final EmployeeController employeeController = new EmployeeController(employeeService);

    @Test
    void shouldCallServiceToGetAllEmployees() {
        List<Employee> expectedEmployees = singletonList(Employee.builder().id(1L).name("srini").departmentId(101L)
                .age(18).build());
        when(employeeService.findAllEmployees()).thenReturn(expectedEmployees);

        Collection<Employee> actualEmployees = employeeController.findAll();

        assertThat(actualEmployees).isEqualTo(expectedEmployees);
    }

    @Test
    void shouldCallServiceToGetEmployeeById() {
        Employee expectedEmployee = Employee.builder().id(1L).name("srini").departmentId(101L).age(18).build();
        when(employeeService.findByEmployeeId(1L)).thenReturn(expectedEmployee);

        Employee actualEmployee = employeeController.findById(1L);

        assertThat(actualEmployee).isEqualTo(expectedEmployee);
    }

    @Test
    void shouldCallServiceToSaveEmployee() {
        Employee actualEmployee = Employee.builder().id(1L).name("srini").departmentId(101L).age(18).build();
        when(employeeService.saveEmployee(actualEmployee)).thenReturn(actualEmployee);

        Employee expectedEmployee = employeeController.saveEmployee(actualEmployee);

        assertThat(actualEmployee).isSameAs(expectedEmployee);
    }
}