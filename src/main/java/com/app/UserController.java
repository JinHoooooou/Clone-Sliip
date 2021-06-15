package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/signUpForm")
  public String signUpForm() {
    return "signUpForm";
  }

  @PostMapping("/create")
  public String create(User user) {
    userService.create(user);
    return "index";
  }
}
