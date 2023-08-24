package com.sunbasedata.assignment.service.impl;
import com.sunbasedata.assignment.dto.JwtRequest;
import com.sunbasedata.assignment.dto.JwtResponse;
import com.sunbasedata.assignment.dto.LogoutSuccessResponseDto;
import com.sunbasedata.assignment.exception.NotAuthorizedException;
import com.sunbasedata.assignment.repository.UserRepository;
import com.sunbasedata.assignment.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
@Configuration
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder bcryptEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.sunbasedata.assignment.entity.User user = userRepository.findByLoginId(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getLoginId(), user.getPassword(),
                new ArrayList<>());
    }

    public JwtResponse authenticate (JwtRequest authenticationRequest) throws NotAuthorizedException {

        authenticate(authenticationRequest.getLoginId(), authenticationRequest.getPassword());
        final UserDetails userDetails = loadUserByUsername(authenticationRequest.getLoginId());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtResponse(token);
    }

    public void authenticate(String username, String rawPassword) throws NotAuthorizedException {

        com.sunbasedata.assignment.entity.User user = userRepository.findByLoginId(username);
        if(user==null){
            throw new NotAuthorizedException("Invalid Username or password");
        }

        String storedPassword = user.getPassword();
        boolean passwordMatches = bcryptEncoder.matches(rawPassword, storedPassword);
        if(!passwordMatches){
            throw new NotAuthorizedException("Invalid Username or password");
        }
    }

    public LogoutSuccessResponseDto logoutUser(String token) {
        if (token != null) {
            jwtTokenUtil.blacklistToken(token);
        }
        return new LogoutSuccessResponseDto("Logout successful for " + jwtTokenUtil.getUsernameFromToken(token), LocalDateTime.now());
    }

}