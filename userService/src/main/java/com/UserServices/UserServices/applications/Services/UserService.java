package com.UserServices.UserServices.applications.Services;


import com.UserServices.UserServices.apis.Dto.UserDTO;
import com.UserServices.UserServices.apis.Resources.InRequest.CreateUserRequest;
import com.UserServices.UserServices.apis.Resources.InRequest.LoginUser;
import com.UserServices.UserServices.apis.Resources.InRequest.ProfileRequest;
import com.UserServices.UserServices.apis.Resources.OutResponse.LoginResponse;
import com.UserServices.UserServices.apis.Resources.OutResponse.ProfileResponse;
import com.UserServices.UserServices.apis.Resources.OutResponse.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService  {
    public UserResponse Register (CreateUserRequest userRequest);
    public LoginResponse Login (LoginUser loginUser);
    public ProfileResponse getProfile (ProfileRequest profileRequest);
}