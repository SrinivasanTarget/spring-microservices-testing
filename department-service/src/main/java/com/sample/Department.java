package com.sample;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Department {
    private Long id;
    private String name;
    private List<Employee> employees = new ArrayList<>();
}
