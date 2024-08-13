package com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.client;

import com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeClient {

    List<EmployeeDTO> getAllEmployee();

    EmployeeDTO getEmployeeById(Long employeeId);

    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);
}
