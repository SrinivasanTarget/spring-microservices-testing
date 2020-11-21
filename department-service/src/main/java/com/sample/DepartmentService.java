package com.sample;

import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department findDepartmentByEmployeeId(Long employeeId) {
        return departmentRepository.findDepartmentByEmployeeId(employeeId);
    }
}
