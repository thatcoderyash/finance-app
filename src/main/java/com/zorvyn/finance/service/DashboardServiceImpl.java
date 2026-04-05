package com.zorvyn.finance.service;

import com.zorvyn.finance.DTO.DashboardResponse;
import com.zorvyn.finance.models.FinancialRecord;
import com.zorvyn.finance.models.Type;
import com.zorvyn.finance.models.User;
import com.zorvyn.finance.repository.FinancialRecordRepository;
import com.zorvyn.finance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    @Override
    public DashboardResponse getUserDashboard(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<FinancialRecord> records = recordRepository.findByCreatedBy(user);

        double totalIncome = records.stream()
                .filter(r -> r.getType() == Type.INCOME)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double totalExpense = records.stream()
                .filter(r -> r.getType() == Type.EXPENSE)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        Map<String, Double> categoryTotals = records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getCategory() != null ? r.getCategory() : "Uncategorized",
                        Collectors.summingDouble(FinancialRecord::getAmount)
                ));

        Map<String, Double> monthlyTrends = records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getDate().getYear() + "-" + String.format("%02d", r.getDate().getMonthValue()),
                        Collectors.summingDouble(FinancialRecord::getAmount)
                ));

        DashboardResponse response = new DashboardResponse();
        response.setTotalIncome(totalIncome);
        response.setTotalExpense(totalExpense);
        response.setNetBalance(totalIncome - totalExpense);
        response.setCategoryTotals(categoryTotals);
        response.setMonthlyTrends(monthlyTrends);

        return response;
    }
}
