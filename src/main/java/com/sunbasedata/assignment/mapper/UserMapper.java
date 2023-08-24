package com.sunbasedata.assignment.mapper;
import com.sunbasedata.assignment.dto.RegisterUserRequestDTO;
import com.sunbasedata.assignment.entity.User;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",imports = LocalDateTime.class)
public interface UserMapper {
    User mapToEntity(RegisterUserRequestDTO requestDTO);
}