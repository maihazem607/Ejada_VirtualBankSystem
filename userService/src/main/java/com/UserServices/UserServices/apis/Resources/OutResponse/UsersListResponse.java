package com.UserServices.UserServices.apis.Resources.OutResponse;


import com.UserServices.UserServices.apis.Dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersListResponse {

    private List<UserDTO> users;

}