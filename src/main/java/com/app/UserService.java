package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public void create(User newUser) {
    userRepository.save(newUser);
  }
}
