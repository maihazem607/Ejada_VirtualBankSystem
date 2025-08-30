package com.UserServices.UserServices.applications.Services.UserServiceImp;

import com.UserServices.UserServices.apis.Resources.InRequest.CreateUserRequest;
import com.UserServices.UserServices.apis.Resources.InRequest.LoginUser;
import com.UserServices.UserServices.apis.Resources.InRequest.ProfileRequest;
import com.UserServices.UserServices.apis.Resources.OutResponse.LoginResponse;
import com.UserServices.UserServices.apis.Resources.OutResponse.ProfileResponse;
import com.UserServices.UserServices.apis.Resources.OutResponse.UserResponse;
import com.UserServices.UserServices.applications.Exceptons.UnAuthorizedException;
import com.UserServices.UserServices.applications.Exceptons.UserAlreadyExistsException;
import com.UserServices.UserServices.applications.Exceptons.UserNotFoundException;
import com.UserServices.UserServices.applications.Models.User;
import com.UserServices.UserServices.applications.Repositories.UserRepo;
import com.UserServices.UserServices.applications.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepo userRepository;


    @Autowired
    private PasswordEncoder encoder;




    @Override
    public UserResponse Register (CreateUserRequest userRequest){

        if(userRepository.existsByUsername(userRequest.getUsername()) || userRepository.existsByEmail(userRequest.getEmail())) {
            throw new UserAlreadyExistsException( HttpStatus.CONFLICT,"Conflict","Username or email already exists.");
        }
        User u = new User();
        u.setUsername(userRequest.getUsername());
        u.setPassword(encoder.encode(userRequest.getPassword())); // hash
        u.setEmail(userRequest.getEmail());
        u.setFirstName(userRequest.getFirstName());
        u.setLastName(userRequest.getLastName());

        User saved = userRepository.save(u);

        return new UserResponse(
                saved.getId(),
                saved.getUsername(),
                "User registered successfully."
        );
    }


    @Override
    public LoginResponse Login (LoginUser loginUser){
        User user= (User) userRepository.findByUsername(loginUser.getUsername())
                .orElseThrow(() ->new UnAuthorizedException("Invalid username or password.") );

        if (encoder.matches(loginUser.getPassword(), user.getPassword()))
        {   LoginResponse loginResponse=new LoginResponse(user.getId(), user.getUsername());
            return  loginResponse;
        }else{
            throw new UnAuthorizedException("Invalid username or password.");
        }
    }




    @Override
    public ProfileResponse getProfile (ProfileRequest profileRequest){
        User user = userRepository.findById(profileRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(profileRequest.getUserId()));
        return new ProfileResponse(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName());
    }




}
