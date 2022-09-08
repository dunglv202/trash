package com.example.shop.controller;

import com.example.shop.entity.User;
import com.example.shop.exception.UserAlreadyExistsException;
import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthenticationController {
    private UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute("user") User user, Model model) {
        List<String> msg = new ArrayList<>();
        try {
            userService.register(user);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(v -> msg.add(v.getMessage()));
        } catch (UserAlreadyExistsException e) {
            msg.add(e.getMessage());
        }
        model.addAttribute("msg", msg);

        if (msg.size() > 0) return "register";

        return "redirect:/register?success";
    }
}
