package applications.Services.AccountServiceImp;

import applications.Exceptons.InvalidUserDataException;
import applications.Models.Account;
import applications.Repositories.AccountRepository;
import applications.Services.AccountService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class AccountServiceImp implements AccountService {

    @Autowired
    private  AccountRepository accounts;


    @Override
    public boolean exists(UUID accountId) {
        Account account =accounts.findById(accountId).orElse(null);
        if(account != null)
            return true;
        else return false;
    }



    @Override
    @Transactional
    public void debit(UUID accountId, BigDecimal amount) {
        Account a = accounts.findById(accountId).orElseThrow(
                ()-> new InvalidUserDataException (HttpStatus.BAD_REQUEST,"Invalid Data","Invalid 'from' or 'to' account ID."));
        if (amount.signum() <= 0) throw new InvalidUserDataException(HttpStatus.BAD_REQUEST,"Invalid Data","Amount must be positive.");
        if (a.getBalance().compareTo(amount) < 0) {
            throw new  InvalidUserDataException
                    ( HttpStatus.BAD_REQUEST,"Invalid Data","Invalid 'from' or 'to' account ID.");
        }
        a.setBalance(a.getBalance().subtract(amount));

    }


    @Override
    @Transactional
    public void credit(UUID accountId, BigDecimal amount) {
       if(amount ==null) {
           throw new InvalidUserDataException(HttpStatus.BAD_REQUEST, "Invalid Data", "Invalid 'from' or 'to' account ID.");
       }
        Account a = accounts.findById(accountId)
                .orElseThrow(() -> new InvalidUserDataException
                 ( HttpStatus.BAD_REQUEST,"Invalid Data","Invalid 'from' or 'to' account ID."));


        a.setBalance(a.getBalance().add(amount));
    }
}