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
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email); // ✅ use email
        System.out.println("User found: "+ user.getId());
        if (user == null) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        System.out.println("User found: 2 "+ user.getId());
        UserRole userRole = user.getRole();
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(userRole.toAuthority())
        );
        System.out.println("User found: 3 "+  authorities);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // ✅ use email as username in security context
                user.getPassword(),
                authorities
        );
    }
}
