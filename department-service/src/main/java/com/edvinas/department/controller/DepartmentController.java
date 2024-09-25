package com.edvinas.department.controller;

import com.edvinas.department.entity.Department;
import com.edvinas.department.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/")
    public Department saveDepartment(@RequestBody Department department) {

        log.info("Inside saveDepartment method of DepartmentController");
        return departmentService.saveDepartment(department);
    }

    @GetMapping("/{id}")
    public Department findDepartmentById(@PathVariable("id") Long departmentId) {
        log.info("Inside findDepartmentById method of DepartmentController");
        return departmentService.findDepartmentById(departmentId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable("id") Long departmentId) {
        log.info("Inside deleteDepartment of DepartmentController");
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok().body("Department with ID " + departmentId + " deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDepartment(@PathVariable("id") Long departmentId, @RequestBody Department department) {
        log.info("Inside updateDepartment of DepartmentController");
        departmentService.updateDepartment(departmentId, department);
        return ResponseEntity.ok("Department with ID " + departmentId + " updated successfully.");
    }

}
