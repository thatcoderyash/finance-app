package com.zorvyn.finance.repository;

import com.zorvyn.finance.models.FinancialRecord;
import com.zorvyn.finance.models.Type;
import com.zorvyn.finance.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {


    // Find all records created by a specific user
    List<FinancialRecord> findByCreatedBy(User user);

    // Filter by type
    List<FinancialRecord> findByCreatedByAndType(User user, Type type);

    // Filter by category
    List<FinancialRecord> findByCreatedByAndCategory(User user, String category);

    // Filter by type and category
    List<FinancialRecord> findByCreatedByAndTypeAndCategory(User user, Type type, String category);

    // Filter by date range
    List<FinancialRecord> findByCreatedByAndDateBetween(User user, LocalDate startDate, LocalDate endDate);

    // Filter by type, category, and date range
    List<FinancialRecord> findByCreatedByAndTypeAndCategoryAndDateBetween(User user, Type type, String category, LocalDate startDate, LocalDate endDate);
}