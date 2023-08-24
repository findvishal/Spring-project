package com.sunbasedata.assignment.mapper;

import com.sunbasedata.assignment.dto.CreateCustomerRequestDTO;
import com.sunbasedata.assignment.dto.CreateCustomerResponseDTO;
import com.sunbasedata.assignment.dto.CustomerResponseDTO;
import com.sunbasedata.assignment.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",imports = LocalDateTime.class)
public interface CustomerMapper {
    CustomerResponseDTO mapToDTO(Customer customer);
    Customer mapToEntity(CreateCustomerRequestDTO customerDTO);

    void updateCustomerFromDTO(CreateCustomerRequestDTO updateRequestDTO, @MappingTarget Customer customer);
}