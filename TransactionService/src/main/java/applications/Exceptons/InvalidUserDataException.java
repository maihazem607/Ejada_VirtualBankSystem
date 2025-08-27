package applications.Exceptons;

import org.springframework.http.HttpStatus;

public class InvalidUserDataException extends ApplicationException {

    public InvalidUserDataException(HttpStatus httpStatus, String errorCode, String message){
        super( httpStatus, errorCode, message);
    }


    public InvalidUserDataException( String s) {
        super( HttpStatus.BAD_REQUEST,"Invalid Data", s);
    }


}
