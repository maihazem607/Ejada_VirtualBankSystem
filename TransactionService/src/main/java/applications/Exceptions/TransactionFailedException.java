package applications.Exceptions;

import org.springframework.http.HttpStatus;

public class TransactionFailedException extends ApplicationException {

    public TransactionFailedException() {
        super("TRANSACTION_FAILED", HttpStatus.BAD_REQUEST,"Transaction failed");
    }
}
