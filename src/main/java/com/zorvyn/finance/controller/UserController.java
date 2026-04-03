package com.zorvyn.finance.controller;

import com.zorvyn.finance.DTO.RegisterRequest;
import com.zorvyn.finance.DTO.UserResponse;
import com.zorvyn.finance.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request){
        UserResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }
}
