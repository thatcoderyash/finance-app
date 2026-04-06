package com.zorvyn.finance.controller;

import com.zorvyn.finance.DTO.request.LoginRequest;
import com.zorvyn.finance.DTO.request.SignUpRequest;
import com.zorvyn.finance.DTO.response.LoginResponse;
import com.zorvyn.finance.DTO.response.UserResponse;
import com.zorvyn.finance.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
        private AuthService authService;

        @PostMapping("/signup")
        public ResponseEntity<UserResponse> register(@RequestBody SignUpRequest request){
            UserResponse response = authService.signUp(request);
            return ResponseEntity.ok(response);
        }

        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
            LoginResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        }


    }

