package com.BFFService.BFFService.applications.Services;


import org.springframework.stereotype.Service;

import com.BFFService.BFFService.apis.Resources.OutResponse.DashboardResponse;

@Service
public interface BFFService {

    DashboardResponse getDashboard(String userId);
}