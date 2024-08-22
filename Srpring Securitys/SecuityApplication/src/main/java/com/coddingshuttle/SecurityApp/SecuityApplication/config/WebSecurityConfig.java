package com.coddingshuttle.SecurityApp.SecuityApplication.config;

import com.coddingshuttle.SecurityApp.SecuityApplication.entities.enums.Role;
import com.coddingshuttle.SecurityApp.SecuityApplication.filters.JwtAuthFilter;
import com.coddingshuttle.SecurityApp.SecuityApplication.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.OAuth2LoginDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.coddingshuttle.SecurityApp.SecuityApplication.entities.enums.Permission.*;
import static com.coddingshuttle.SecurityApp.SecuityApplication.entities.enums.Role.ADMIN;
import static com.coddingshuttle.SecurityApp.SecuityApplication.entities.enums.Role.CREATOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true) //allow @secured and Security Methods
public class WebSecurityConfig {

    private  final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final String[] publicRoute={
            "/error", "/auth/**", "/home.html"
    };

    /**
     *Summary
        * This code sets up a basic security configuration for a Spring Boot application using Spring Security.
        * It defines access rules for different URL patterns, disables CSRF protection, manages user sessions as stateless, and sets up an in-memory user store with encoded passwords.
     *
     * @Configuration: Indicates that this class is a configuration class.
     * @EnableWebSecurity: Enables Spring Security’s web security support and provides the Spring MVC integration.

     *securityFilterChain
        * Configures HTTP security settings.
        * httpSecurity: This is a configuration object provided by Spring Security to customize web security settings.
        * authorizeHttpRequests: Specifies URL patterns and their access rules.
                                * /posts, /error, /public/**: Accessible by everyone.
                                 * /posts/**: Accessible only by users with the ADMIN role.
        * anyRequest().authenticated(): All other requests require authentication.
        * csrf(csrfConfig -> csrfConfig.disable()): Disables CSRF protection.
        * sessionManagement: Configures session management to be stateless, meaning the server won’t maintain user sessions.
        *
        *
        *
     * @param httpSecurity
     * authorizeRequests() restricts access based on RequestMatcher implementations.
     * authenticated() requires that all endpoints called be authenticated before proceeding in the filter chain
     * csrf() to cofigure the csrf protection
     * formLogin() calls the default FormLoginConfigurer class that loads the login page to  authenticate via username-password and accordingly redirects to corresponding failure or success
     *
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterchain(HttpSecurity httpSecurity) throws Exception {
        //configure the SFC
        //All the requests now go through SFC (Each has to be authenticated before it is moved through various routes)

        httpSecurity.authorizeHttpRequests(auth-> auth
                        //public route no authentication required
                .requestMatchers(publicRoute).permitAll()

                        //only ADMIN can access this route
//                .requestMatchers("/posts/**").hasRole(ADMIN.name())

                //Allow GET Requests for all users
//               .requestMatchers(HttpMethod.GET, "/posts/**").permitAll()

                //Allow Post request only for admin and creator
                     //   .requestMatchers(HttpMethod.POST, "/posts/**").hasAnyRole(ADMIN.name(), CREATOR.name())
                        .requestMatchers("/posts/**").authenticated()

                //Assigning Permissions
                        .requestMatchers(HttpMethod.POST, "/posts/**").hasAnyAuthority(POST_CREATE.name())
                        .requestMatchers(HttpMethod.GET, "/posts/**").hasAuthority(POST_VIEW.name())
                        .requestMatchers(HttpMethod.PUT, "/posts/**").hasAuthority(POST_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE, "/posts/**").hasAuthority(POST_DELETE.name())

                        //any other request made in the routes need to be authenticated first(login)
                .anyRequest().authenticated())


                .csrf(httpSecurityCsrfConfigurer ->httpSecurityCsrfConfigurer.disable())
                        .sessionManagement(httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //Add Custom Filter to the securityFilterchain, Before the  UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                //oauth2 config(successHandler(redirect on success authentication))
                .oauth2Login(httpSecurityOAuth2LoginConfigurer ->
                        httpSecurityOAuth2LoginConfigurer.failureUrl("/login?error=true")
                                                         .successHandler(oAuth2SuccessHandler));

        //custom login form(here login takes place at be html file)
       // httpSecurity.formLogin(formLoginConfig->formLoginConfig.loginPage("/newLogin.html"));

        //Default login page for csrf and session Id (Front end and Backend on the same server)
       // httpSecurity.formLogin(Customizer.withDefaults());

        return httpSecurity.build(); //returns securityFilter chain

  }

    /**
     * User.withUsername("admin")
         * User is a class provided by Spring Security that implements the UserDetails interface. It is used to create user instances.
         * withUsername("admin") is a static method in the User class that returns a User.UserBuilder object.
        * User.UserBuilder is an inner static class within the User class, used to build User objects using a fluent API.
     * @return
     */
//    @Bean
//    UserDetailsService myInMemoryUserDetailsService() {
//        UserDetails normalUser = User
//                .withUsername("anuj")
//                .password(passwordEncoder().encode("Anuj123"))
//                .roles("USER")
//                .build();
//
//        UserDetails adminUser = User
//                .withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(normalUser, adminUser);
//    }



    //Authentication Manager Config(to use authenticate method)
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
       return configuration.getAuthenticationManager();

    }
}
