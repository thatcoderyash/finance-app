package com.zorvyn.finance.security;

import com.zorvyn.finance.DTO.LoginRequest;
import com.zorvyn.finance.DTO.LoginResponse;
import com.zorvyn.finance.DTO.SignUpRequest;
import com.zorvyn.finance.DTO.UserResponse;
import com.zorvyn.finance.mapper.UserMapper;
import com.zorvyn.finance.models.Role;
import com.zorvyn.finance.models.Status;
import com.zorvyn.finance.models.User;
import com.zorvyn.finance.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;

    public UserResponse signUp(SignUpRequest request){

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

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        assert userDetails != null;
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = authUtil.generateAccessToken(user);

        return new LoginResponse(token, user.getId());
    }

    public UserResponse assignRole(Long userId, Role role) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(role);

        return UserMapper.toDTO(userRepository.save(user));
    }

    public UserResponse updateStatus(Long userId, Status status) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(status);

        return UserMapper.toDTO(userRepository.save(user));
    }


}
