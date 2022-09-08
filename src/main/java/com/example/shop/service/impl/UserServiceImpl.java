package com.example.shop.service.impl;

import com.example.shop.entity.User;
import com.example.shop.exception.UserAlreadyExistsException;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Transactional
@Validated
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }

    @Override
    public User register(@Valid User user) {
        // check if username exist
        User foundUser = userRepo.findUserByUsername(user.getUsername());
        if (foundUser != null) throw new UserAlreadyExistsException(user.getUsername());

        // encrypt password save user to db
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepo.save(user);

        return user;
    }
}
