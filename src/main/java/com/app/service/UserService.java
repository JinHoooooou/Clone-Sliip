package com.app.service;

import com.app.dto.User;
import com.app.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public void create(User newUser) {
    userRepository.save(newUser);
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUserInfo(String userId) {
    return userRepository.findByUserId(userId);
  }

  public User login(String userId, String password) {
    User user = userRepository.findByUserId(userId);
    if (user == null) {
      return null;
    }
    if (!password.equals(user.getPassword())) {
      return null;
    }

    return user;
  }
}
