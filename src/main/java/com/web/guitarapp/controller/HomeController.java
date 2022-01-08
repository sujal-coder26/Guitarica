package com.web.guitarapp.controller;

import com.web.guitarapp.dao.UserRepository;
import com.web.guitarapp.entities.User;
import com.web.guitarapp.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class HomeController {

  private final BCryptPasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Autowired
  public HomeController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/home")
  public String base() {
    return "home";
  }

  @GetMapping("/signup")
  public String signup(Model model) {
    model.addAttribute("title", "SignUp - Guitarica");
    model.addAttribute("user", new User());
    return "registration";
  }

  @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
  public String signupUser(
          @Valid @ModelAttribute("user") User user, BindingResult result,
          @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
          Model model, HttpSession httpSession) {
   try {
     if (!agreement) {
       System.out.println("Please agree the terms and conditions.");
       throw new Exception("Please agree the terms and conditions.");
     }
     if(result.hasErrors()){
       System.out.println("Error Found " + result.toString());
       model.addAttribute("user", user);
       return "registration";
     }

     user.setRole("USER");
     user.setEnabled(true);
     user.setImageurl("image1.png");
//     user.setPassword(passwordEncoder.encode(user.getPassword()));

     System.out.println("Agreement: " + agreement);
     System.out.println("User" + user);
     User saveUser = this.userRepository.save(user);
     model.addAttribute("user", new User());
     httpSession.setAttribute("message", new Message("Successfully Signed Up", "alert-success" ));
     return "registration";
   }
   catch (Exception e){
     e.printStackTrace();
     model.addAttribute("user", user);
     httpSession.setAttribute("message", new Message("Server Error!!! " + e.getMessage(), "alert-danger" ));
     return "registration";
   }

  }
}
