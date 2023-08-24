package com.sunbasedata.assignment.service;

import com.sunbasedata.assignment.dto.CreateCustomerRequestDTO;
import com.sunbasedata.assignment.dto.CreateCustomerResponseDTO;
import com.sunbasedata.assignment.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {
    CreateCustomerResponseDTO createCustomer(CreateCustomerRequestDTO requestDTO);

    List<CustomerResponseDTO> getAllCustomers();

    String deleteCustomerByUUID(Long uuid);

    String updateCustomerByUUID(Long uuid, CreateCustomerRequestDTO updateRequestDTO);
}
