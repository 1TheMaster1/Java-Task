package com.example.task.service;

import com.example.task.dto.EmployeeRequest;
import com.example.task.dto.EmployeeResponse;
import com.example.task.entity.Employee;
import com.example.task.exception.ResourceNotFoundException;
import com.example.task.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeServiceImpl(EmployeeRepository repo) {
        this.repo = repo;
    }

    @Override
    public EmployeeResponse create(EmployeeRequest req) {
        if (repo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        Employee e = new Employee();
        e.setName(req.getName());
        e.setEmail(req.getEmail());
        e.setDepartment(req.getDepartment());
        repo.save(e);
        return mapToResponse(e);
    }

    @Override
    public List<EmployeeResponse> getAll() {
        return repo.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public EmployeeResponse getById(Long id) {
        Employee e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        return mapToResponse(e);
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest req) {
        Employee e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        e.setName(req.getName());
        e.setEmail(req.getEmail());
        e.setDepartment(req.getDepartment());
        return mapToResponse(e);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id " + id);
        }
        repo.deleteById(id);
    }

    private EmployeeResponse mapToResponse(Employee e) {
        EmployeeResponse r = new EmployeeResponse();
        r.setId(e.getId());
        r.setName(e.getName());
        r.setEmail(e.getEmail());
        r.setDepartment(e.getDepartment());
        r.setCreatedAt(e.getCreatedAt());
        r.setUpdatedAt(e.getUpdatedAt());
        return r;
    }
}