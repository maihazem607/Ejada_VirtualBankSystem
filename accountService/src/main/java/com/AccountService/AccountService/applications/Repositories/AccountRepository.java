package com.AccountService.AccountService.applications.Repositories;

import com.AccountService.AccountService.applications.Models.Account;
import com.AccountService.AccountService.applications.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    List<Account> findByUserId(String userUUID);

    List<Account> findByStatusAndUpdatedAtBefore(AccountStatus status, Timestamp timestamp);

    List<Account> findByStatus(AccountStatus status);
}
