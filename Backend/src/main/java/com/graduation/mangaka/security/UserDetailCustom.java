package com.graduation.mangaka.security;

import com.graduation.mangaka.model.User;
import com.graduation.mangaka.model.TypeAndRole.UserRole;
import com.graduation.mangaka.repository.UserRepository;
import com.graduation.mangaka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component("userDetailsService")
public class UserDetailCustom implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username/password");
        }
        UserRole userRole = user.getRole(); // Role (e.g., Role.USER)
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(userRole.toAuthority())
        );

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                authorities
        );
    }
}