package com.UserServices.UserServices.applications.Repositories;



import com.UserServices.UserServices.applications.Models.UserLoginEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserLoginEventRepo extends JpaRepository<UserLoginEvent, UUID> {
}