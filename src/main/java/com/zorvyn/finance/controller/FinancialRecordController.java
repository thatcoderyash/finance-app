package com.zorvyn.finance.controller;

import com.zorvyn.finance.DTO.FinancialRecordRequest;
import com.zorvyn.finance.DTO.FinancialRecordResponse;
import com.zorvyn.finance.service.FinancialRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class FinancialRecordController {

    private final FinancialRecordService service;

    @PostMapping("/user/{userId}")
    public FinancialRecordResponse create(
            @RequestBody @Valid FinancialRecordRequest dto,
            @PathVariable Long userId
    ) {
        return service.createRecord(dto, userId);
    }

    @GetMapping
    public List<FinancialRecordResponse> getAll() {
        return service.getAllRecords();
    }

    @PutMapping("/{recordId}/user/{userId}")
    public FinancialRecordResponse update(
            @PathVariable Long recordId,
            @PathVariable Long userId,
            @RequestBody @Valid FinancialRecordRequest dto
    ) {
        return service.updateRecord(recordId, dto, userId);
    }

    @DeleteMapping("/{recordId}/user/{userId}")
    public void delete(
            @PathVariable Long recordId,
            @PathVariable Long userId
    ) {
        service.deleteRecord(recordId, userId);
    }

    @GetMapping("/filter/user/{userId}")
    public List<FinancialRecordResponse> filter(
            @PathVariable Long userId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return service.filterRecords(userId, type, category, startDate, endDate);
    }
}