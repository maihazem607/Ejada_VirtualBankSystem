package com.UserServices.UserServices.applications.Services;

import com.UserServices.UserServices.applications.Models.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginServices {
    public void recordLogin(User user, boolean success);
}
