package com.coddingshuttle.SecurityApp.SecuityApplication.entities;

import com.coddingshuttle.SecurityApp.SecuityApplication.entities.enums.Permission;
import com.coddingshuttle.SecurityApp.SecuityApplication.entities.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(unique = true)
    private  String email;

    private  String password;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //what kind of activity this user can perform, How is the user authorized(roles and authorities are same in SS)
           Set<SimpleGrantedAuthority> authorities=  roles.stream()  //convert set of roles to stream
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))  //create instance of SimpleGrantedAuthority and pass the role name(ADMIN,USER...)
                .collect(Collectors.toSet()); //convert stream back to set

        //Loop Through every (permissions) and Add permissions to the authorities
        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.name())));

        return authorities;

    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
