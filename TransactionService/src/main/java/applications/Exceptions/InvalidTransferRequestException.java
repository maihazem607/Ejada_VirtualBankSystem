package applications.Exceptions;

import org.springframework.http.HttpStatus;


public class InvalidTransferRequestException extends ApplicationException {

    public InvalidTransferRequestException() {
        super("BAD_REQUEST", HttpStatus.BAD_REQUEST, "Invalid 'from' or 'to' account ID or insufficient funds");
    }

}