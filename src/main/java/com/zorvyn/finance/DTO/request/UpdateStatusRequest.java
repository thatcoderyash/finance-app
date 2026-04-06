package com.zorvyn.finance.DTO.request;

import com.zorvyn.finance.models.Status;
import lombok.Data;

@Data
public class UpdateStatusRequest {
    private Status status;
}
