package com.coddingshuttle.SecurityApp.SecuityApplication.controllers;

import com.coddingshuttle.SecurityApp.SecuityApplication.dto.LoginDto;
import com.coddingshuttle.SecurityApp.SecuityApplication.dto.LoginResponseDTO;
import com.coddingshuttle.SecurityApp.SecuityApplication.dto.SignupDto;
import com.coddingshuttle.SecurityApp.SecuityApplication.dto.UserDTo;
import com.coddingshuttle.SecurityApp.SecuityApplication.services.AuthService;
import com.coddingshuttle.SecurityApp.SecuityApplication.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private  final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDTo> signUp(@RequestBody  SignupDto signupDto){

        return new ResponseEntity<>(userService.signup(signupDto), HttpStatus.OK);

    }

    /**ACCESS+REFRESH TOKEN is sent to Front end
     *store the JWT token(Refresh) inside Cookie and send it to Frontend/client
     * @param loginDto (email,password),  HttpServletResponse response
     * @return LoginResponseDTO
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDto loginDto, HttpServletResponse response){

        //ACCESS+REFRESH TOKEN is sent to Front end
       LoginResponseDTO loginResponseDTO=authService.login(loginDto);

       //store the JWT token(Refresh) inside Cookie and send it to Frontend/client
        Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);       //cookie can only be accessed by http method, not by js
        cookie.setSecure("production".equals(deployEnv));      //set this true only in prd instance.
        response.addCookie(cookie);     //add cookie to HTTP Response

        return  new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){
        //1. get the refreshToken from the Http request cookie
                        //Array of all the cookies is returned
        //2.check if you are able to find the refreshToken cookie in array of cookie
                    //convert Array of Cookie to Stream()
       String refreshToken= Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))  //try to find matching cookie
                .findFirst()
                .map(cookie -> cookie.getValue())  //get the value from matching cookie
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));

       //Use the Refresh Token from to cookie to generate new Access Token
        LoginResponseDTO loginResponseDTO=authService.refreshToken(refreshToken);

       return new ResponseEntity<>(loginResponseDTO,HttpStatus.OK);

    }




}
