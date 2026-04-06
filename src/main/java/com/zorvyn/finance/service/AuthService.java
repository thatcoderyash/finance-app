package com.zorvyn.finance.service;

import com.zorvyn.finance.DTO.request.LoginRequest;
import com.zorvyn.finance.DTO.response.LoginResponse;
import com.zorvyn.finance.DTO.request.SignUpRequest;
import com.zorvyn.finance.DTO.response.UserResponse;
import com.zorvyn.finance.exceptions.EmailAlreadyExistsException;
import com.zorvyn.finance.exceptions.UserNotFoundException;
import com.zorvyn.finance.mapper.UserMapper;
import com.zorvyn.finance.models.Role;
import com.zorvyn.finance.models.Status;
import com.zorvyn.finance.models.User;
import com.zorvyn.finance.repository.UserRepository;
import com.zorvyn.finance.utils.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;

    public UserResponse signUp(SignUpRequest request){

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = UserMapper.toEntity(request, passwordEncoder);
        User savedUser = userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }

    public LoginResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(Objects.requireNonNull(userDetails).getUsername())
                .orElseThrow(() -> new UserNotFoundException(userDetails.getUsername()));

        String token = authUtil.generateAccessToken(user);

        return new LoginResponse(token, user.getId());
    }

    public UserResponse assignRole(Long userId, Role role) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setRole(role);

        return UserMapper.toDTO(userRepository.save(user));
    }

    public UserResponse updateStatus(Long userId, Status status) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setStatus(status);

        return UserMapper.toDTO(userRepository.save(user));
    }


}
