package com.sunbasedata.assignment.service.impl;

import com.sunbasedata.assignment.dto.RegisterUserRequestDTO;
import com.sunbasedata.assignment.dto.RegisterUserResponseDTO;
import com.sunbasedata.assignment.entity.User;
import com.sunbasedata.assignment.mapper.UserMapper;
import com.sunbasedata.assignment.repository.UserRepository;
import com.sunbasedata.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public RegisterUserResponseDTO registerUser(RegisterUserRequestDTO requestDTO) {
        if (userRepository.existsByLoginId(requestDTO.getLoginId())) {
            RegisterUserResponseDTO responseDTO = new RegisterUserResponseDTO();
            responseDTO.setStatusCode(400);
            responseDTO.setMessage("User already exists");
            return responseDTO;
        }

        User user = userMapper.mapToEntity(requestDTO);
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        userRepository.save(user);
        RegisterUserResponseDTO responseDTO = new RegisterUserResponseDTO();
        responseDTO.setStatusCode(201);
        responseDTO.setMessage("User registered successfully");
        return responseDTO;
    }
}
