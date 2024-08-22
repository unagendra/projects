package com.coddingshuttle.SecurityApp.SecuityApplication.services;

import com.coddingshuttle.SecurityApp.SecuityApplication.dto.LoginDto;
import com.coddingshuttle.SecurityApp.SecuityApplication.dto.LoginResponseDTO;
import com.coddingshuttle.SecurityApp.SecuityApplication.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

private final AuthenticationManager authenticationManager;
private final JwtService jwtService;
private final UserService userService;
private final SessionService sessionService;

    public LoginResponseDTO login(LoginDto loginDto) {
        //1create object of Authentication using AM authenticate()
     Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

     //2.get the Authenticated user using getPrincipal() of Authentication interface.
     User user= (User) authentication.getPrincipal();

     //3.generate the Token for the user and return it back
        String accessToken=jwtService.generateAccessToken(user);
        String refreshToken=jwtService.generateRefreshToken(user);

        //4.Create New Session for the user
        sessionService.generateNewSession(user,refreshToken);

        return new LoginResponseDTO(user.getId(),accessToken,refreshToken);
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        //1.generate the Access Token-> get the userID from the token, Get the user to generate the token
        Long userId=jwtService.getUserIdFromToken(refreshToken); //2.verify if RT is expired or not

        //3.verify if there is session associated with the Refresh Token
        sessionService.validateSession(refreshToken);

        User user=userService.getUserById(userId);

        //4.Generate the Access Token for the user.
       String accessToken= jwtService.generateAccessToken(user);

       return new LoginResponseDTO(user.getId(), accessToken, refreshToken);

    }
}
