package com.web.guitarapp.controller;

import com.web.guitarapp.entities.User;
import com.web.guitarapp.exception.ApiException;
import com.web.guitarapp.helper.Message;
import com.web.guitarapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.web.guitarapp.util.Constants.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

  private final UserService userService;

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
      model.addAttribute("user", user);
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
  public String returnDashboard(){
    return "dashboard";
  }
}
