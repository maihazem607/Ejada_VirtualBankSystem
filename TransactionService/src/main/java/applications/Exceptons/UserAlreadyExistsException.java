package applications.Exceptons;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ApplicationException {

    public UserAlreadyExistsException() {
        super( HttpStatus.BAD_REQUEST, "User already exists", "USER_ALREADY_EXISTS");
    }

    public UserAlreadyExistsException(HttpStatus httpStatus, String errorCode, String message) {
        super( httpStatus, errorCode, message);
    }
}
