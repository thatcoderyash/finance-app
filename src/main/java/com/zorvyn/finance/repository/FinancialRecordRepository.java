package com.zorvyn.finance.repository;

import com.zorvyn.finance.models.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByCreatedById(Long userId);
}