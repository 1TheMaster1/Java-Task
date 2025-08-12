package com.example.task.service;

import com.example.task.dto.EmployeeRequest;
import com.example.task.dto.EmployeeResponse;
import java.util.List;

public interface EmployeeService {
    EmployeeResponse create(EmployeeRequest req);
    List<EmployeeResponse> getAll();
    EmployeeResponse getById(Long id);
    EmployeeResponse update(Long id, EmployeeRequest req);
    void delete(Long id);
}
