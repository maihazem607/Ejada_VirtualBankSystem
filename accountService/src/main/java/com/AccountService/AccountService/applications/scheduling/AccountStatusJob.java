package com.AccountService.AccountService.applications.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.AccountService.AccountService.applications.Models.Account;
import com.AccountService.AccountService.applications.Repositories.AccountRepository;
import com.AccountService.AccountService.applications.enums.AccountStatus;
import java.sql.Timestamp;
import java.util.List;

@Component
public class AccountStatusJob {

    @Autowired
    private AccountRepository accountRepository;


    @Scheduled(cron = "0 0 */1 * * *")
    public void updateAccountStatus() {
       
        Timestamp oneDayAgo = new Timestamp(System.currentTimeMillis() - (24 * 60 * 60 * 1000));

        // TODO: Add logic from transaction service after it's done
        //the logic is not right we need to check the transaction endpoint not the updated at
        List<Account> accountsToInactivate = accountRepository.findByStatusAndUpdatedAtBefore(AccountStatus.ACTIVE, oneDayAgo);

        for (Account account : accountsToInactivate) {
            account.setStatus(AccountStatus.INACTIVE);
        }

        accountRepository.saveAll(accountsToInactivate);
    }
}
