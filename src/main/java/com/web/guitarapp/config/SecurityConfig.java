package com.web.guitarapp.config;

import com.web.guitarapp.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // using the configuration annotations
@EnableWebSecurity // using this to enable spring security support
@Slf4j

public class SecurityConfig extends WebSecurityConfigurerAdapter { // using this class in order to customize
  // WebSecurity and HttpSecurity

  @Bean //Declairing Bean
  public UserDetailsService getUserDetailService() { // this interface will load the user's specific data
    return new UserDetailsServiceImpl(); // returning the object
  }

  @Bean//Declairing Bean
  public BCryptPasswordEncoder passwordEncoder() { // using this class for the password encryption
    return new BCryptPasswordEncoder(); //returning the object

  }

  @Bean//Declairing Bean
  public DaoAuthenticationProvider authenticationProvider() { // this class will retreives all the user details from service class UserDetailService
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(); // creating the object
    daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService()); // setting the user's details by getting the details
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // setting the encodded password

    return daoAuthenticationProvider; //returning the object
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception { // this class will create an authentication manager
    auth.authenticationProvider(authenticationProvider()); // class used for the authentication implementation
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception { // this is for handling the http requests
    http.authorizeRequests()
        .antMatchers("/admin/**")//providing the ant pattern
        .hasRole("ADMIN") // having the role ADMIN
        .antMatchers("/user/**") //providing the ant pattern
        .hasRole("USER") // having the role user
        .antMatchers("/**")
        .permitAll()// permitting all
        .and() // and also
        .formLogin() // setting for the login
            .loginPage("/login")// setting login page
            .usernameParameter("email")// the first parameter is email
            .passwordParameter("password")// the second parameter is password
            .defaultSuccessUrl("/dashboard")// if the login is successfull will send to dashboard
        .and() // and also
        .csrf()
        .disable(); // disabling the csrf
  }
}
