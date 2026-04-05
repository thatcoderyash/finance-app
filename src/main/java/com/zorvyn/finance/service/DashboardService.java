package com.zorvyn.finance.service;

import com.zorvyn.finance.DTO.DashboardResponse;

public interface DashboardService {
    DashboardResponse getUserDashboard(Long userId);
}
