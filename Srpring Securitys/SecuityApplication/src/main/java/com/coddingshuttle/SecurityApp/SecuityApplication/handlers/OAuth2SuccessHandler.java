package com.coddingshuttle.SecurityApp.SecuityApplication.handlers;

import com.coddingshuttle.SecurityApp.SecuityApplication.entities.User;
import com.coddingshuttle.SecurityApp.SecuityApplication.services.JwtService;
import com.coddingshuttle.SecurityApp.SecuityApplication.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtService jwtService;

    @Value("${deploy.env}")
    private String deployEnv;


    /**
     * Overide onAuthenticationSuccess
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //1.Convert the authentication object to OAuth2Token
        OAuth2AuthenticationToken token= (OAuth2AuthenticationToken) authentication;

        //2.Get the Principal(user details) from the Token
       DefaultOAuth2User oAuth2User= (DefaultOAuth2User) token.getPrincipal();

       //3. get the email of the user who sending login request from the client
       log.info(oAuth2User.getAttribute("email"));

       //4.If the user is already present, log the user.

        String email=oAuth2User.getAttribute("email");

        User user=userService.getUserByEmail(email);

        //4.If the user is not present then create the user and generate the JWT Tokens
        if (user==null){
            //create user object and save it to User Table
            User newUser = User.builder()
                    .name(oAuth2User.getAttribute("name"))
                    .email(email)
                    .build();

            User savedUser=userService.save(newUser);
        }

        //5. Generate Access and Refresh Token
        String accessToken=jwtService.generateAccessToken(user);
        String refreshToken=jwtService.generateRefreshToken(user);

        //6. Set the Refresh Token inside the Cookie before returning it to client

        Cookie cookie = new Cookie("refreshToken",refreshToken);
        cookie.setHttpOnly(true);       //cookie can only be accessed by http method, not by js
        cookie.setSecure("production".equals(deployEnv));      //set this true only in prd instance.
        response.addCookie(cookie);     //add cookie to HTTP Response

        //7. send the Access Token back to client along with the Front end url

        String frontEndUrl = "http://localhost:8080/home.html?token="+accessToken;

        //8. Redirect to FrontEndUrl upon successful login (FrontendURL+ACCESS TOKEN)

//        getRedirectStrategy().sendRedirect(request, response, frontEndUrl);
        response.sendRedirect(frontEndUrl);

    }
}
