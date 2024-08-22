package com.coddingshuttle.SecurityApp.SecuityApplication.dto;

import lombok.Data;

@Data
public class UserDTo {
    //password is only for signupDTO, In user it is not required(This is returned back to Front End)
    private Long id;
    private String email;
    private String name;
}
