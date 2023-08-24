package com.sunbasedata.assignment.service;

import com.sunbasedata.assignment.dto.RegisterUserRequestDTO;
import com.sunbasedata.assignment.dto.RegisterUserResponseDTO;

public interface UserService {
    RegisterUserResponseDTO registerUser(RegisterUserRequestDTO requestDTO);
}
