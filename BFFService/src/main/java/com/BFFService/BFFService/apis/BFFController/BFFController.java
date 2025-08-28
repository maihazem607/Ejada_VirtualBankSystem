package com.BFFService.BFFService.apis.BFFController;





import com.BFFService.BFFService.applications.Services.BFFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BFFController {

    @Autowired
    private BFFService bffService;

}
