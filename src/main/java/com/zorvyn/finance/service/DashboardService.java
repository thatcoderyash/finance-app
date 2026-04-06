package com.zorvyn.finance.service;

import com.zorvyn.finance.DTO.response.DashboardResponse;

public interface DashboardService {
    DashboardResponse getUserDashboard(Long userId);
}
