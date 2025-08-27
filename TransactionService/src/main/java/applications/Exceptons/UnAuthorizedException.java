package applications.Exceptons;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends ApplicationException {

    public UnAuthorizedException(HttpStatus httpStatus, String errorCode, String message) {
        super( httpStatus, errorCode, message);
    }

    public UnAuthorizedException() {
        super( HttpStatus.UNAUTHORIZED,"Invalid credentials", "UNAUTHORIZED");
    }

    public UnAuthorizedException(String message) {
        super( HttpStatus.UNAUTHORIZED, "UNAUTHORIZED",message);
    }

}
