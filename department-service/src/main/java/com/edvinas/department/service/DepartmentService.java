package com.edvinas.department.service;

import com.edvinas.department.entity.Department;
import com.edvinas.department.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department saveDepartment(Department department) {
        log.info("Inside saveDepartment of DepartmentService");
        return departmentRepository.save(department);
    }

    public Department findDepartmentById(Long departmentId) {
        log.info("Inside findDepartmentById of DepartmentService");
        return departmentRepository.findByDepartmentId(departmentId);
    }

    public void deleteDepartment(Long departmentId) {
        log.info("Inside deleteDepartment of DepartmentService");
        departmentRepository.deleteById(departmentId);
    }

    public Department updateDepartment(Long departmentId, Department departmentDetails) {
        log.info("Inside updateDepartment of DepartmentService");
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));

        department.setDepartmentName(departmentDetails.getDepartmentName());
        department.setDepartmentAddress(departmentDetails.getDepartmentAddress());
        department.setDepartmentCode(departmentDetails.getDepartmentCode());

        return departmentRepository.save(department);
    }

}
