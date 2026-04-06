package com.zorvyn.finance.DTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FinancialRecordRequest {

    @NotNull
    @Positive
    private Double amount;

    @NotNull
    private String type;

    private String category;

    private LocalDate date;

    private String description;

}
