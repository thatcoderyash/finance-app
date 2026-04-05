package com.zorvyn.finance.mapper;

import com.zorvyn.finance.DTO.SignUpRequest;
import com.zorvyn.finance.DTO.UserResponse;
import com.zorvyn.finance.models.Role;
import com.zorvyn.finance.models.Status;
import com.zorvyn.finance.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserMapper {

    public static User toEntity(SignUpRequest request, PasswordEncoder passwordEncoder) {
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.VIEWER);
        user.setStatus(Status.ACTIVE);

        return user;
    }

    public static UserResponse toDTO(User user) {
        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setStatus(user.getStatus().name());

        return response;
    }

}
