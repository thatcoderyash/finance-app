package com.zorvyn.finance.service;

import com.zorvyn.finance.DTO.request.FinancialRecordRequest;
import com.zorvyn.finance.DTO.response.FinancialRecordResponse;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordService {

    FinancialRecordResponse createRecord(FinancialRecordRequest dto, Long userId);

    List<FinancialRecordResponse> getAllRecords();

    FinancialRecordResponse updateRecord(Long recordId, FinancialRecordRequest dto, Long userId);

    void deleteRecord(Long recordId, Long userId);

    List<FinancialRecordResponse> filterRecords(Long userId, String type, String category, LocalDate startDate, LocalDate endDate);
}