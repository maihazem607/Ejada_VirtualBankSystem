package com.UserServices.UserServices.applications.Services.LoginServiceImp;

import com.UserServices.UserServices.applications.Models.User;
import com.UserServices.UserServices.applications.Models.UserLoginEvent;
import com.UserServices.UserServices.applications.Repositories.UserLoginEventRepo;
import com.UserServices.UserServices.applications.Services.LoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginServiceImp implements LoginServices {

    @Autowired
    UserLoginEventRepo userLoginEventRepo;

    @Override
    public void recordLogin(User user, boolean success) {
        UserLoginEvent event = new UserLoginEvent();
        event.setUser(user);
        event.setSuccess(success);
        event.setLoginTime(LocalDateTime.now());
        userLoginEventRepo.save(event);
    }

}
