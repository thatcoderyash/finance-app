package com.zorvyn.finance.service;

import com.zorvyn.finance.DTO.FinancialRecordRequest;
import com.zorvyn.finance.DTO.FinancialRecordResponse;
import com.zorvyn.finance.mapper.FinancialRecordMapper;
import com.zorvyn.finance.models.FinancialRecord;
import com.zorvyn.finance.models.User;
import com.zorvyn.finance.repository.FinancialRecordRepository;
import com.zorvyn.finance.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialRecordService {

    private final FinancialRecordRepository recordRepo;
    private final UserRepository userRepo;

    public FinancialRecordService(FinancialRecordRepository recordRepo, UserRepository userRepo) {
        this.recordRepo = recordRepo;
        this.userRepo = userRepo;
    }

    public FinancialRecordResponse createRecord(FinancialRecordRequest dto, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FinancialRecord record = FinancialRecordMapper.toEntity(dto, user);

        FinancialRecord saved = recordRepo.save(record);

        return FinancialRecordMapper.toDTO(saved);
    }

    public List<FinancialRecordResponse> getAllRecords() {
        return recordRepo.findAll()
                .stream()
                .map(FinancialRecordMapper::toDTO)
                .collect(Collectors.toList());
    }
}