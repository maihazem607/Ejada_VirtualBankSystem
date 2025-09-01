package com.UserServices.UserServices.apis.Resources.OutResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
        private String userId;
        private String username;
        private String email;
        private String firstName;
        private String lastName;


}
