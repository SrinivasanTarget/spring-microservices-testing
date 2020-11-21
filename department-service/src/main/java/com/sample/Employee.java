package com.sample;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Employee {

    @Id
    private Long id;
    private Long departmentId;
    private String name;
    private int age;

    public Employee(Employee employee) {
        this.age = employee.age;
        this.name = employee.name;
        this.departmentId = employee.departmentId;
        this.id = employee.id;
    }
}