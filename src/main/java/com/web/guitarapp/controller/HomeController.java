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

import static com.web.guitarapp.util.Constants.REGISTRATION;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

  private final UserService userService;

  @RequestMapping("/home")
  public String base(Model model) {
    model.addAttribute("title", "Home - Guitarica");
    return "home";
  }

  @GetMapping("/signup")
  public String signup(Model model) {
    model.addAttribute("title", "SignUp - Guitarica");
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
      return REGISTRATION;
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("user", user);
      httpSession.setAttribute(
          "message", new Message("Server Error!!! " + e.getMessage(), "alert-danger"));
      return REGISTRATION;
    }
  }
}
