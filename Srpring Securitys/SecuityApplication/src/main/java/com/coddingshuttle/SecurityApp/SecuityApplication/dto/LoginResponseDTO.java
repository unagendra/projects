package com.coddingshuttle.SecurityApp.SecuityApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponseDTO {

//Used to Return Access and Refresh Token to Front end.
    private Long id;
    private String accessToken;
    private String refreshToken;
}
