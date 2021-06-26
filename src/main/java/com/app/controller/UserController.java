package com.app.controller;

import com.app.dto.User;
import com.app.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/login")
  public String loginForm() {
    return "users/login";
  }

  @PostMapping("/login")
  public String login(String userId, String password, HttpSession session) {
    User user = userService.login(userId, password);
    if(user == null) {
      return "users/loginFailed";
    }

    session.setAttribute("user", user);
    return "redirect:/";
  }

  @GetMapping("/signUpForm")
  public String signUpForm() {
    return "users/signUpForm";
  }

  @PostMapping("")
  public String create(User user) {
    userService.create(user);
    return "redirect:";
  }

  @GetMapping("")
  public String list(Model model) {
    model.addAttribute("users", userService.getAllUsers());
    return "users/list";
  }

  @GetMapping("{userId}")
  public String getUser(@PathVariable String userId, Model model) {
    model.addAttribute("user", userService.getUserInfo(userId));

    return "users/userInfo";
  }
}
