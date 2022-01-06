package com.web.guitarapp.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER")
@Data
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String password;

  private String username;

  @Column(unique = true)
  private String email;

  private String phoneNumber;

  private boolean enabled;

  private String role;

  @Column(length = 500)
  private String about;

  private String imageurl;

  @Override
  public String toString() {
    return "User{"
        + "id="
        + id
        + ", password='"
        + password
        + '\''
        + ", username='"
        + username
        + '\''
        + ", email='"
        + email
        + '\''
        + ", phoneNumber='"
        + phoneNumber
        + '\''
        + ", enabled="
        + enabled
        + ", role='"
        + role
        + '\''
        + ", about='"
        + about
        + '\''
        + ", imageurl='"
        + imageurl
        + '\''
        + '}';
  }
}
