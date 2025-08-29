package Applications.Services.ServiceImp;




import Apis.DTO.LogEntryResponseDTO;
import Apis.DTO.LogMessageDTO;
import Applications.Models.Enums.MessageType;
import Applications.Models.Log;
import Applications.Repositories.LogRepository;
import Applications.Services.LogingServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoggingService implements LogingServices {

    private final LogRepository logRepository;

    public void saveLog(LogMessageDTO dto) {
        Log log = new Log();
        log.setMessage(dto.getMessage());
        log.setMessageType(MessageType.valueOf(dto.getMessageType().toUpperCase()));
        log.setDateTime(dto.getDateTime());
        logRepository.save(log);
    }

    public List<LogEntryResponseDTO> getAllLogs() {
        return logRepository.findAll().stream().map(log -> {
            LogEntryResponseDTO dto = new LogEntryResponseDTO();
            dto.setId(log.getId());
            dto.setMessage(log.getMessage());
            dto.setMessageType(log.getMessageType().name());
            dto.setDateTime(log.getDateTime());
            return dto;
        }).collect(Collectors.toList());
    }
}