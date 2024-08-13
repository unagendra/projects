package com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.client.impl;

import com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.advice.ApiResponse;
import com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.client.EmployeeClient;
import com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.dto.EmployeeDTO;
import com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private  final      RestClient restClient;

    Logger  log=LoggerFactory.getLogger(EmployeeClientImpl.class);


    @Override
    public List<EmployeeDTO> getAllEmployee() {
        log.trace("Trying to retrieve all employees in getAllEmployees");

        try{
            log.info("Attempting to call the restClient Method in getAllEmployees");
           ApiResponse <List<EmployeeDTO>>  employeeDTO= restClient.get()  //GET REQUEST
                    .uri("employees")  //append to BASE URL
                     .retrieve()           //retrieve all the data
                      .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {   //Handling Error in Rest Client
                       log.error(new String(res.getBody().readAllBytes()));   //log.error is recommended
                           throw new ResourceNotFoundException("could not create the employee");
                                })
                   .body(new ParameterizedTypeReference<>() {  //To convert data to particular type of object
                            });
            log.debug("Successfully retrieved the employees in getAllEmployees");
            log.trace("Retrieved employees list in getAllEmployees :{}", employeeDTO.getData());
            return employeeDTO.getData();
        } catch (Exception e){
            log.error("Exception occurred Here", e);
            throw  new RuntimeException(e);
        }

    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        log.trace("Trying to retrieve employees by id in getEmployeeById: {}",employeeId);


        try {
            log.info("Attempting to call the restClient Method in getEmployeeById");
            ApiResponse<EmployeeDTO> employeeResponse = restClient.get()
                    .uri("employees/{employeeId}", employeeId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res)->{
                       log.error(new  String(res.getBody().readAllBytes()));
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });

            log.trace("Successfully retrieved the employee: {}",employeeResponse.getData());
            return employeeResponse.getData();
        } catch (Exception e) {
            log.error("Exception occurred Here", e);
            throw new RuntimeException(e);
        }

    }


    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        log.trace("Trying to create Employee with information {}", employeeDTO);
        try {
          ResponseEntity<ApiResponse<EmployeeDTO>> employeeDTOApiResponse = restClient.post()
                    .uri("employees")
                    .body(employeeDTO)   //Request Body
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> { //Handling Error in Rest Client
                        log.error(new String(res.getBody().readAllBytes())); //log is recommended
                        throw new ResourceNotFoundException("could not create the employee");
                    })
                    .toEntity(new ParameterizedTypeReference<>() { //return response Entity (body+ HTTP STATUS CODE+Timestamp)
                       //.body(new ParameterizedTypeReference<>() {// Response Body
                    });
            log.trace("Successfully created a new employee : {}", employeeDTOApiResponse.getBody());
            return employeeDTOApiResponse.getBody().getData();
        }
        catch (Exception e) {
            log.error("Exception occurred in createNewEmployee", e);
            throw new RuntimeException(e);
        }
    }
}
