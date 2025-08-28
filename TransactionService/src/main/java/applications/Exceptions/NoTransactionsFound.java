package applications.Exceptions;

import org.springframework.http.HttpStatus;

public class NoTransactionsFound extends ApplicationException {

    public NoTransactionsFound(String id) {
        super("NOT_FOUND",HttpStatus.NOT_FOUND, "No transactions found for the given account ID: " + id);
    }
    
}
