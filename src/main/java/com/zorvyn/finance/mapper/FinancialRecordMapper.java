package com.zorvyn.finance.mapper;

import com.zorvyn.finance.DTO.FinancialRecordRequest;
import com.zorvyn.finance.DTO.FinancialRecordResponse;
import com.zorvyn.finance.models.FinancialRecord;
import com.zorvyn.finance.models.User;

public class FinancialRecordMapper {

    public static FinancialRecord toEntity(FinancialRecordRequest dto, User user) {
        FinancialRecord record = new FinancialRecord();

        record.setAmount(dto.getAmount());
        record.setType(FinancialRecord.Type.valueOf(dto.getType()));
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setDescription(dto.getDescription());
        record.setCreatedBy(user);

        return record;
    }

    public static FinancialRecordResponse toDTO(FinancialRecord record) {
        FinancialRecordResponse dto = new FinancialRecordResponse();

        dto.setId(record.getId());
        dto.setAmount(record.getAmount());
        dto.setType(record.getType().name());
        dto.setCategory(record.getCategory());
        dto.setDate(record.getDate());
        dto.setDescription(record.getDescription());
        dto.setCreatedBy(record.getCreatedBy().getId());

        return dto;
    }
}