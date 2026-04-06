package com.zorvyn.finance.DTO.response;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String status;
}
