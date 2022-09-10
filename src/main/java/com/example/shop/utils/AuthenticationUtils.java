package com.example.shop.utils;

import com.example.shop.entity.User;
import com.example.shop.security.auth.ApplicationUserDetails;
import org.springframework.security.core.Authentication;

public class AuthenticationUtils {
    public static User getUser(Authentication auth) {
        if (auth==null || !auth.isAuthenticated()) throw new RuntimeException("Unauthenticated");
        return ((ApplicationUserDetails) auth.getPrincipal()).getUser();
    }
}
