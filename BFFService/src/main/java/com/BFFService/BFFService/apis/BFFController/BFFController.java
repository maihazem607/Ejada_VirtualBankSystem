package com.BFFService.BFFService.apis.BFFController;





import com.BFFService.BFFService.apis.Resources.InRequest.DashboardRequest;
import com.BFFService.BFFService.apis.Resources.OutResponse.DashboardResponse;
import com.BFFService.BFFService.applications.Services.BFFService;
import com.BFFService.BFFService.applications.producer.RequestLoggerProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BFFController {

    @Autowired
    private BFFService bffService;

    @Autowired
    private RequestLoggerProducer requestLoggerProducer;

    @GetMapping("/bff/dashboard/{userId}")
    public ResponseEntity<DashboardResponse> getDashboard(@PathVariable String userId) {
        DashboardRequest dashboardRequest =new DashboardRequest(userId);
        requestLoggerProducer.log(dashboardRequest.toString(),"request");
        DashboardResponse dashboard = bffService.getDashboard(dashboardRequest.getUserId());
        requestLoggerProducer.log(dashboard.toString(),"response");
        return ResponseEntity.ok(dashboard);
    }
}
