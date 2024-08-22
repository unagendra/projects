package com.coddingshuttle.SecurityApp.SecuityApplication.services;

import com.coddingshuttle.SecurityApp.SecuityApplication.dto.SignupDto;
import com.coddingshuttle.SecurityApp.SecuityApplication.dto.UserDTo;
import com.coddingshuttle.SecurityApp.SecuityApplication.entities.User;
import com.coddingshuttle.SecurityApp.SecuityApplication.exceptions.ResourceNotFoundException;
import com.coddingshuttle.SecurityApp.SecuityApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

//Load the email/username from the repository
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                ()->new BadCredentialsException("User with email "+ username +" not found"));
    }


    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id "+ userId +
                " not found"));
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);

    }




    public UserDTo signup(SignupDto signupDto) {
        //verify if the user(Email) is already present
       Optional<User> user= userRepository.findByEmail(signupDto.getEmail());
       if (user.isPresent()){
           throw  new BadCredentialsException("User is already Present with email "+signupDto.getEmail());
       }

       //User does not exists, Encode the password,

        //password encoding(BCrypt)
        String password= passwordEncoder.encode(signupDto.getPassword());
        signupDto.setPassword(password);

//        Create new user and save it to database
        User needToCreateUser=mapper.map(signupDto,User.class);
        User savedUser=userRepository.save(needToCreateUser);

       return mapper.map(savedUser,UserDTo.class);

    }

    public User save(User newUser) {
       return userRepository.save(newUser);
    }
}
