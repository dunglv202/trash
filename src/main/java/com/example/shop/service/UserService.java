package com.example.shop.service;

import com.example.shop.entity.User;

import javax.validation.Valid;

public interface UserService {
    User findByUsername(String username);
    User register(@Valid User user);
}
