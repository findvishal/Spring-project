package com.sunbasedata.assignment.service.impl;

import com.sunbasedata.assignment.dto.CreateCustomerRequestDTO;
import com.sunbasedata.assignment.dto.CreateCustomerResponseDTO;
import com.sunbasedata.assignment.dto.CustomerResponseDTO;
import com.sunbasedata.assignment.entity.Customer;
import com.sunbasedata.assignment.exception.CustomerCreationException;
import com.sunbasedata.assignment.mapper.CustomerMapper;
import com.sunbasedata.assignment.repository.CustomerRepository;
import com.sunbasedata.assignment.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CreateCustomerResponseDTO createCustomer(CreateCustomerRequestDTO requestDTO) {
        if (requestDTO.getFirst_name() == null || requestDTO.getLast_name() == null || requestDTO.getFirst_name() == "" || requestDTO.getLast_name() == "") {
            throw new CustomerCreationException("First Name or Last Name is missing");
        }

        Customer customer = customerMapper.mapToEntity(requestDTO);
        customerRepository.save(customer);

        return new CreateCustomerResponseDTO(HttpStatus.CREATED.value(), "Successfully Created");
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteCustomerByUUID(Long uuid) {
        Optional<Customer> optionalCustomer = customerRepository.findByUuid(uuid);
        if (optionalCustomer.isPresent()) {
            customerRepository.delete(optionalCustomer.get());
            return "Successfully deleted";
        } else {
            return "UUID not found";
        }
    }

    @Override
    public String updateCustomerByUUID(Long uuid, CreateCustomerRequestDTO updateRequestDTO) {
        Optional<Customer> optionalCustomer = customerRepository.findByUuid(uuid);
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            customerMapper.updateCustomerFromDTO(updateRequestDTO, existingCustomer);
            customerRepository.save(existingCustomer);
            return "Successfully Updated";
        } else {
            return "UUID not found";
        }
    }
}
