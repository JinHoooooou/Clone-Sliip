package com.app;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, length = 15)
  private String userId;
  @Column(nullable = false, length = 10)
  private String password;
  @Column(nullable = false)
  private String name;
  private String email;
}
