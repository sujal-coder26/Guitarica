package com.web.guitarapp.controller;

import com.web.guitarapp.dao.CourseTrackerRepository;
import com.web.guitarapp.dao.UserRepository;
import com.web.guitarapp.entities.User;
import com.web.guitarapp.exception.ApiException;
import com.web.guitarapp.helper.Message;
import com.web.guitarapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.security.Principal;
import java.sql.ResultSet;

import static com.web.guitarapp.util.Constants.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

  private final UserService userService;
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CourseTrackerRepository courseTrackerRepository;
  @RequestMapping("/")
  public String base(Model model) {
    model.addAttribute(TITLE, "Home - Guitarica");
    return "home";
  }

  @GetMapping("/signup")
  public String signup(Model model) {
    model.addAttribute(TITLE, "SignUp - Guitarica");
    model.addAttribute("user", new User());
    return REGISTRATION;
  }

  @PostMapping(value = "/doRegister")
  public String signupUser(
      @Valid @ModelAttribute("user") User user,
      BindingResult result,
      @RequestParam(value = "agreement") boolean agreement,
      Model model,
      HttpSession httpSession) {
    try {
      if (!agreement) {
        log.error("Please agree the terms and conditions.");
        throw new ApiException("Please agree the terms and conditions.");
      }
      if (result.hasErrors()) {
        log.error("Error Found {}", result);
        model.addAttribute("user", user);
        return REGISTRATION;
      }
      userService.save(user);
      model.addAttribute("user", new User());
      httpSession.setAttribute("message", new Message("Successfully Signed Up", "alert-success"));
      return LOGIN;

    } catch (Exception e) {
      log.error("Exception occurred: ", e);
      model.addAttribute("usaaaaaaaaaer", user);
      httpSession.setAttribute(
          "message", new Message("Server Error!!! " + e.getMessage(), "alert-danger"));
      return REGISTRATION;
    }
  }

  @GetMapping("/signin")
  public String customLogin(Model model) {
    model.addAttribute(TITLE, "Guitarica-LoginPage");
    return LOGIN;
  }

  @PostMapping("/login_error_handler")
  public String loginFailureHandler() {
    log.warn("Login failed.");
    return LOGIN;
  }

  @GetMapping("/dashboard")
  public String returnDashboard(Model model, Principal principal){
    String username = principal.getName();
    System.out.println("Username: " + username);
    User user = userRepository.getUserByUserName(username);
    System.out.println(user);

    model.addAttribute("user", user);
    return "dashboard";
  }
  @GetMapping("/beginnerLevel")
  public String beginnerLevel( ){
    Object info = courseTrackerRepository.getUserLevel(1);
    System.out.println(info);
    return "BeginnerLevel";
  }

  @GetMapping("/guitartuner")
  public String guitartuner(){
    return "tuner";
  }
  @GetMapping("/intermediateLevel")
  public String intermediateLevel(){
    return "IntermediateLevel";
  }

  @GetMapping("/advanceLevel")
  public String advanceLevel(){
    return "AdvanceLevel";
  }
  @GetMapping("/completed")
  public String completed(){
    return "certificate";
  }
  }

