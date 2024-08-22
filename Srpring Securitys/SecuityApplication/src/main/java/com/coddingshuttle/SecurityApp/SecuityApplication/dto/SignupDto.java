package com.coddingshuttle.SecurityApp.SecuityApplication.dto;

import com.coddingshuttle.SecurityApp.SecuityApplication.entities.enums.Permission;
import com.coddingshuttle.SecurityApp.SecuityApplication.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignupDto {
    //provide Sign up information from Front end
    private String email;
    private String password;
    private String name;

    private Set<Role> roles;  //only admin can onboard new roles
    private Set<Permission> permissions;
}
