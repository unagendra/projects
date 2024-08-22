package com.coddingshuttle.SecurityApp.SecuityApplication.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    /**
     * Method Declaration:
     * PasswordEncoder passwordEncoder() {: This method is named passwordEncoder and declares that it returns an object of type PasswordEncoder.
     * new BCryptPasswordEncoder() Initialization:

     * new BCryptPasswordEncoder() creates an instance of BCryptPasswordEncoder.
     * BCryptPasswordEncoder is an implementation of the PasswordEncoder interface provided by Spring Security.
     * Return Statement:

     * return new BCryptPasswordEncoder(); returns the newly created BCryptPasswordEncoder instance as a bean.
     * This bean is managed by the Spring container and can be injected into other Spring-managed components.
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
