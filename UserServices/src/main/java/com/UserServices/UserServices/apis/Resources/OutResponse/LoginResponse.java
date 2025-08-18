package com.UserServices.UserServices.apis.Resources.OutResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private UUID userId;
    private String username;

}
