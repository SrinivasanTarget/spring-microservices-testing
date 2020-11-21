package com.sample;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface DepartmentRepository extends MongoRepository<Department, Long> {

    @Query(value = "{ 'employees': { $elemMatch: { 'id' : ?0 } }}")
    Department findDepartmentByEmployeeId(Long id);
}
