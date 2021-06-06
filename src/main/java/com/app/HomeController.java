package com.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

  @GetMapping("")
  public String home() {
    return "index";
  }

  @GetMapping("/signUpForm")
  public String signUpForm() {
    return "signUpForm";
  }
}
