package applications.Exceptions;

import org.springframework.http.HttpStatus;

public class TransactionNotFoundException extends ApplicationException {

    public TransactionNotFoundException() {
        super("NOT_FOUND", HttpStatus.NOT_FOUND,"Transaction not found");
    }
    
}
