package com.zorvyn.finance.service;

import com.zorvyn.finance.DTO.request.FinancialRecordRequest;
import com.zorvyn.finance.DTO.response.FinancialRecordResponse;
import com.zorvyn.finance.mapper.FinancialRecordMapper;
import com.zorvyn.finance.models.FinancialRecord;
import com.zorvyn.finance.models.Type;
import com.zorvyn.finance.models.User;
import com.zorvyn.finance.repository.FinancialRecordRepository;
import com.zorvyn.finance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialRecordServiceImpl implements FinancialRecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    @Override
    public FinancialRecordResponse createRecord(FinancialRecordRequest dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FinancialRecord record = FinancialRecordMapper.toEntity(dto, user);
        record.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());

        return FinancialRecordMapper.toDTO(recordRepository.save(record));
    }

    @Override
    public List<FinancialRecordResponse> getAllRecords() {
        return recordRepository.findAll().stream()
                .map(FinancialRecordMapper::toDTO)
                .toList();
    }

    @Override
    public FinancialRecordResponse updateRecord(Long recordId, FinancialRecordRequest dto, Long userId) {
        FinancialRecord record = recordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if (!record.getCreatedBy().getId().equals(userId)) {
            throw new RuntimeException("Not authorized to update this record");
        }

        record.setAmount(dto.getAmount());
        record.setType(Type.valueOf(dto.getType()));
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setDescription(dto.getDescription());

        return FinancialRecordMapper.toDTO(recordRepository.save(record));
    }

    @Override
    public void deleteRecord(Long recordId, Long userId) {
        FinancialRecord record = recordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if (!record.getCreatedBy().getId().equals(userId)) {
            throw new RuntimeException("Not authorized to delete this record");
        }

        recordRepository.delete(record);
    }

    @Override
    public List<FinancialRecordResponse> filterRecords(Long userId, String type, String category, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<FinancialRecord> records;

        if (type != null && category != null && startDate != null && endDate != null) {
            records = recordRepository.findByCreatedByAndTypeAndCategoryAndDateBetween(
                    user, Type.valueOf(type), category, startDate, endDate);
        } else if (type != null && category != null) {
            records = recordRepository.findByCreatedByAndTypeAndCategory(user, Type.valueOf(type), category);
        } else if (type != null) {
            records = recordRepository.findByCreatedByAndType(user, Type.valueOf(type));
        } else if (category != null) {
            records = recordRepository.findByCreatedByAndCategory(user, category);
        } else if (startDate != null && endDate != null) {
            records = recordRepository.findByCreatedByAndDateBetween(user, startDate, endDate);
        } else {
            records = recordRepository.findByCreatedBy(user);
        }

        return records.stream().map(FinancialRecordMapper::toDTO).toList();
    }
}