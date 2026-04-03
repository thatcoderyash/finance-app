package com.zorvyn.finance.controller;

import com.zorvyn.finance.DTO.FinancialRecordRequest;
import com.zorvyn.finance.DTO.FinancialRecordResponse;
import com.zorvyn.finance.service.FinancialRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
public class FinancialRecordController {

    private final FinancialRecordService service;

    public FinancialRecordController(FinancialRecordService service) {
        this.service = service;
    }

    @PostMapping("/user/{userId}")
    public FinancialRecordResponse create(
            @RequestBody FinancialRecordRequest dto,
            @PathVariable Long userId
    ) {
        return service.createRecord(dto, userId);
    }

    @GetMapping
    public List<FinancialRecordResponse> getAll() {
        return service.getAllRecords();
    }
}