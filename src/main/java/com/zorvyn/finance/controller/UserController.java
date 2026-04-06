package com.zorvyn.finance.controller;

import com.zorvyn.finance.DTO.request.UpdateRoleRequest;
import com.zorvyn.finance.DTO.request.UpdateStatusRequest;
import com.zorvyn.finance.DTO.response.UserResponse;
import com.zorvyn.finance.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class UserController {
        private final AuthService authService;

        @PutMapping("/{id}/role")
        public ResponseEntity<UserResponse> assignRole(
                @PathVariable Long id,
                @RequestBody UpdateRoleRequest request) {

            return ResponseEntity.ok(authService.assignRole(id, request.getRole()));
        }

        @PutMapping("/{id}/status")
        public ResponseEntity<UserResponse> updateStatus(
                @PathVariable Long id,
                @RequestBody UpdateStatusRequest request) {

            return ResponseEntity.ok(authService.updateStatus(id, request.getStatus()));
        }
}
