package com.AccountService.AccountService.apis.Resources.OutResponse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private int status;// numeric HTTP status (e.g., 400, 404)
    private String error; // your internal error code
    private String message;

}
