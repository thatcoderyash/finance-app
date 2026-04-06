package com.zorvyn.finance.DTO.response;

import lombok.Data;

import java.util.Map;

@Data
public class DashboardResponse {

    private Double totalIncome;
    private Double totalExpense;
    private Double netBalance;
    private Map<String, Double> categoryTotals; // category -> sum
    private Map<String, Double> monthlyTrends;  // "2026-04" -> sum
}
