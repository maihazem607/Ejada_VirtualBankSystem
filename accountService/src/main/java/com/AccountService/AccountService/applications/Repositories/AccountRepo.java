package com.AccountService.AccountService.applications.Repositories;

import com.AccountService.AccountService.applications.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepo extends JpaRepository<Account, UUID> {
}
