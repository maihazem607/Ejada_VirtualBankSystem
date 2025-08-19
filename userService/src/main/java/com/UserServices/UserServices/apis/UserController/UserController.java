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



    @GetMapping("/{userId}/profile")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable UUID userId) {
        ProfileRequest R =new ProfileRequest(userId);
        ProfileResponse profileResponse=userService.getProfile(R);

        return ResponseEntity.ok(profileResponse);
    }



    @PostMapping("/register")
    public UserResponse registerNewUser(@Valid @RequestBody CreateUserRequest userRequest){
        UserResponse userResponse = userService.Register(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse).getBody();

    }

    @PostMapping("/login")
    public LoginResponse Login(@Valid @RequestBody LoginUser loginUser){
        LoginResponse loginResponse =userService.Login(loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse).getBody();
    }



}
