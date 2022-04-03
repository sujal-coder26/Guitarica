package com.web.guitarapp.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Entity
@Table(name = "USER")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Password field can't be empty")
  @Size(min = 8, message = "Password should be minimum of 8 characters ")
  private String password;

  @NotBlank(message = "Name field can't be empty")
  @Size(min = 2, max = 25, message = "Characters number should be between 2-25 ")
  private String username;

  @Column(unique = true)
  @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email")
  private String email;

  private String phoneNumber;

  private boolean enabled;

  private String role;

  @Column(length = 500)
  @NotBlank(message = "About field can't be empty")
  private String about;

  @Column(nullable = true, length = 64)
  private String imageUrl;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    User user = (User) o;
    return id != null && Objects.equals(id, user.id);
  }
//@Transient
//public String getPhotosPath(){
//    if (imageUrl == null || id == null);
//      return "/user-photos/" + id + "/" + imageUrl;
//}

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
