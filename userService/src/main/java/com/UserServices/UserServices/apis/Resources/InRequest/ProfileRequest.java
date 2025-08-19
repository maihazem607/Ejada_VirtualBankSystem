package com.UserServices.UserServices.apis.Resources.InRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {
    private UUID userId;

}
