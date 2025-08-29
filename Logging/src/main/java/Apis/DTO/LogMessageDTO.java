package Apis.DTO;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LogMessageDTO {
    private String message;
    private String messageType;
    private LocalDateTime dateTime;
}