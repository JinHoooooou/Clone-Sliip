package com.app.controller;

import com.app.dto.User;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/signUpForm")
  public String signUpForm() {
    return "signUpForm";
  }

  @PostMapping("")
  public String create(User user) {
    userService.create(user);
    return "redirect:";
  }
}
