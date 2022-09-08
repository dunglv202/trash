package com.example.shop.service;

import com.example.shop.entity.User;

public interface UserService {
    User findByUsername(String username);
}
