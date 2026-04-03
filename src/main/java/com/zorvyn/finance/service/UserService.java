package com.zorvyn.finance.service;

import com.zorvyn.finance.DTO.RegisterRequest;
import com.zorvyn.finance.DTO.UserResponse;
import com.zorvyn.finance.mapper.UserMapper;
import com.zorvyn.finance.models.User;
import com.zorvyn.finance.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request){

        // check if email exists
        if (userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        // DTO -> Entity
        User user = UserMapper.toEntity(request, passwordEncoder);

        // save
        User savedUser = userRepository.save(user);

        // Entity -> DTO
        return UserMapper.toDTO(savedUser);
    }
}