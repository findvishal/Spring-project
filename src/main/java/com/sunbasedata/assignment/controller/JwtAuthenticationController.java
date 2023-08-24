package com.sunbasedata.assignment.controller;

import com.sunbasedata.assignment.dto.*;
import com.sunbasedata.assignment.security.JwtTokenUtil;
import com.sunbasedata.assignment.service.UserService;
import com.sunbasedata.assignment.service.impl.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/sunbase/portal/api")
public class JwtAuthenticationController {
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register",consumes = "application/json")
    public ResponseEntity<RegisterUserResponseDTO> registerUser(@RequestBody RegisterUserRequestDTO requestDTO) {
        RegisterUserResponseDTO responseDTO = userService.registerUser(requestDTO);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }
    @PostMapping(value = "/assignment_auth",consumes = "application/json")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody JwtRequest user) {
        JwtRequest authenticationRequest = new JwtRequest(user.getLoginId(), user.getPassword());
        JwtResponse response = userDetailsService.authenticate(authenticationRequest);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/logoutUser")
    public ResponseEntity<LogoutSuccessResponseDto> logoutUser() {
        return ResponseEntity.ok(userDetailsService.logoutUser(JwtTokenUtil.extractTokenFromHeader(request)));
    }
}