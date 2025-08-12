package com.example.task.controller;

import com.example.task.dto.EmployeeRequest;
import com.example.task.dto.EmployeeResponse;
import com.example.task.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService svc;

    public EmployeeController(EmployeeService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeRequest req) {
        EmployeeResponse created = svc.create(req);
        return ResponseEntity.created(URI.create("/employees/" + created.getId())).body(created);
    }

    @GetMapping
    public List<EmployeeResponse> list() {
        return svc.getAll();
    }

    @GetMapping("/{id}")
    public EmployeeResponse get(@PathVariable Long id) {
        return svc.getById(id);
    }

    @PutMapping("/{id}")
    public EmployeeResponse update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest req) {
        return svc.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
