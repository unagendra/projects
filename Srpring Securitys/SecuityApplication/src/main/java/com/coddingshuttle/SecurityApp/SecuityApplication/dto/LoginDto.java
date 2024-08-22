package com.coddingshuttle.SecurityApp.SecuityApplication.dto;

import lombok.Data;

@Data
public class LoginDto {
    //login from the Frontend
    private String email;
    private String password;
}
