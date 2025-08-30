package Apis.Controller;

import Apis.DTO.LogEntryResponseDTO;
import Apis.DTO.LogMessageDTO;
import Applications.Services.LogingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/log")
public class KafkaController {


    @Autowired
    LogingServices logingServices;

    @GetMapping
    public List<LogEntryResponseDTO> getAllLogs(){
        return logingServices.getAllLogs();
    }

    @PostMapping("/save")
    public void saveLog(LogMessageDTO dto){
        logingServices.saveLog(dto);
    }

}
