package com.AccountService.AccountService.applications.Repositories;

import com.AccountService.AccountService.applications.Models.Account;
import com.AccountService.AccountService.applications.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findByUserId(UUID userUUID);

    List<Account> findByStatusAndUpdatedAtBefore(AccountStatus status, Timestamp timestamp);

    List<Account> findByStatus(AccountStatus status);
}
