package com.UserServices.UserServices.apis.UserController;





import com.UserServices.UserServices.apis.Dto.UserDTO;
import com.UserServices.UserServices.apis.Resources.InRequest.CreateUserRequest;
import com.UserServices.UserServices.apis.Resources.InRequest.LoginUser;
import com.UserServices.UserServices.apis.Resources.InRequest.ProfileRequest;
import com.UserServices.UserServices.apis.Resources.InRequest.UpdateUserRequest;
import com.UserServices.UserServices.apis.Resources.OutResponse.LoginResponse;
import com.UserServices.UserServices.apis.Resources.OutResponse.ProfileResponse;
import com.UserServices.UserServices.apis.Resources.OutResponse.UserResponse;
import com.UserServices.UserServices.apis.Resources.OutResponse.UsersListResponse;
import com.UserServices.UserServices.applications.Services.UserService;
import com.UserServices.UserServices.applications.producer.RequestLoggerProducer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    RequestLoggerProducer requestLoggerProducer;


    @GetMapping("/{userId}/profile")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable String userId) {
        ProfileRequest R =new ProfileRequest(userId);
        requestLoggerProducer.log(R.toString(),"request");
        ProfileResponse profileResponse=userService.getProfile(R);
        requestLoggerProducer.log(profileResponse.toString(),"response");
        return ResponseEntity.ok(profileResponse);
    }



    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerNewUser(@Valid @RequestBody CreateUserRequest userRequest){
        requestLoggerProducer.log(userRequest.toString(),"request");
        UserResponse userResponse = userService.Register(userRequest);
        requestLoggerProducer.log(userResponse.toString(),"response");
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse>Login(@Valid @RequestBody LoginUser loginUser){
        requestLoggerProducer.log(loginUser.toString(),"request");
        LoginResponse loginResponse =userService.Login(loginUser);
        requestLoggerProducer.log(loginResponse.toString(),"response");
        return ResponseEntity.ok(loginResponse);
    }



}
