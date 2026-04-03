package com.zorvyn.finance.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FinancialRecordResponse {

    private Long id;
    private Double amount;
    private String type;
    private String category;
    private LocalDate date;
    private String description;
    private Long createdBy;
}
