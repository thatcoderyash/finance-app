package com.zorvyn.finance.controller;

import com.zorvyn.finance.DTO.response.DashboardResponse;
import com.zorvyn.finance.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/user/{userId}")
    public DashboardResponse getDashboard(@PathVariable Long userId) {
        return dashboardService.getUserDashboard(userId);
    }
}
