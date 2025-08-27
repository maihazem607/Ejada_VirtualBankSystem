package applications.Exceptons;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApplicationException {

    public UserNotFoundException() {
        super( HttpStatus.NOT_FOUND, "Transaction not found", "TRANSACTION_NOT_FOUND");
    }

    public UserNotFoundException(HttpStatus httpStatus, String errorCode, String message) {
        super( httpStatus,  errorCode, message);
    }


}
