package com.AccountService.AccountService.applications.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AccountStatusJob.class);
    @Autowired
    private AccountRepository accountRepository;


    @Scheduled(cron = "0 */5 * * * *")
    public void updateAccountStatus() {
        logger.info("Starting account status update job.");
        Timestamp oneDayAgo = new Timestamp(System.currentTimeMillis() - (24 * 60 * 60 * 1000));

        List<Account> accountsToInactivate = accountRepository.findByStatusAndUpdatedAtBefore(AccountStatus.ACTIVE, oneDayAgo);
        logger.info("Found {} accounts to inactivate.", accountsToInactivate.size());

        for (Account account : accountsToInactivate) {
            logger.info("Inactivating account: {}", account.getAccountNumber());
            account.setStatus(AccountStatus.INACTIVE);
        }

        accountRepository.saveAll(accountsToInactivate);
        logger.info("Finished account status update job.");
    }
}
