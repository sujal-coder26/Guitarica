package com.web.guitarapp.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "USER")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Password field can't be empty")
//    @Size(min = 2, max = 25, message = "Characters number should be between 2-25 ")
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
//    @Size(min = 2, max = 25, message = "Characters number should be between 2-500 ")
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
