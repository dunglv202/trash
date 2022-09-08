package com.example.shop.security.auth;

import com.example.shop.entity.User;
import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ApplicationUserDetailsService implements UserDetailsService {
    private UserService userService;

    public ApplicationUserDetailsService() {
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userService.findByUsername(username);
        if (foundUser == null) throw new UsernameNotFoundException(username + " not found");

        return new ApplicationUserDetails(foundUser);
    }
}
