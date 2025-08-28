package com.BFFService.BFFService.apis.BFFController;





import com.BFFService.BFFService.apis.Resources.OutResponse.DashboardResponse;
import com.BFFService.BFFService.applications.Services.BFFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BFFController {

    @Autowired
    private BFFService bffService;

    @GetMapping("/bff/dashboard/{userId}")
    public ResponseEntity<DashboardResponse> getDashboard(@PathVariable String userId) {
        DashboardResponse dashboard = bffService.getDashboard(userId);
        return ResponseEntity.ok(dashboard);
    }
}
