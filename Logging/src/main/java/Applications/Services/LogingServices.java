package Applications.Services;

import Apis.DTO.LogEntryResponseDTO;
import Apis.DTO.LogMessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogingServices {
    public void saveLog(LogMessageDTO dto);
    public List<LogEntryResponseDTO> getAllLogs();
}
