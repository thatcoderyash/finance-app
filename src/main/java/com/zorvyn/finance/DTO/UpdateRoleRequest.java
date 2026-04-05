package com.zorvyn.finance.DTO;

import com.zorvyn.finance.models.Role;
import lombok.Data;

@Data
public class UpdateRoleRequest {
    private Role role;
}
