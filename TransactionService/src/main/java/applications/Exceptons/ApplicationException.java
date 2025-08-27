package applications.Exceptons;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;



@Data
@AllArgsConstructor
public abstract class ApplicationException extends RuntimeException {

    private final String error;
    private final HttpStatus httpStatus;


    public ApplicationException(HttpStatus httpStatus, String errorCode, String message) {
        super(message);
        this.error = errorCode;
        this.httpStatus = httpStatus;
    }


}
