package com.sunbasedata.assignment.controller;

import com.sunbasedata.assignment.dto.CreateCustomerRequestDTO;
import com.sunbasedata.assignment.dto.CreateCustomerResponseDTO;
import com.sunbasedata.assignment.dto.CustomerResponseDTO;
import com.sunbasedata.assignment.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/sunbase/portal/api")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/assignment",consumes = "application/json")
    public ResponseEntity<CreateCustomerResponseDTO> createCustomer(@RequestBody CreateCustomerRequestDTO requestDTO) {
        CreateCustomerResponseDTO responseDTO = customerService.createCustomer(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(value = "/assignment",consumes = "application/json")
    public ResponseEntity<List<CustomerResponseDTO>> getCustomerList() {
        List<CustomerResponseDTO> customerList = customerService.getAllCustomers();
        return ResponseEntity.ok(customerList);
    }

    @DeleteMapping(value = "/assignment/{uuid}",consumes = "application/json")
    public ResponseEntity<String> handleRequest(@PathVariable("uuid") Long uuid) {
        String result = customerService.deleteCustomerByUUID(uuid);
        return ResponseEntity.status(result.equals("Successfully deleted") ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(result);
    }
    @PutMapping(value = "/assignment/{uuid}",consumes = "application/json")
    public ResponseEntity<String> updateCustomer(
            @PathVariable("uuid") Long uuid,
            @RequestBody CreateCustomerRequestDTO updateRequestDTO) {
        String result = customerService.updateCustomerByUUID(uuid, updateRequestDTO);
        return ResponseEntity.status(result.equals("Successfully Updated") ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(result);
    }
}
