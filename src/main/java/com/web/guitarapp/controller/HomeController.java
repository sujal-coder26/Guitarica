package com.web.guitarapp.controller;

import com.web.guitarapp.dao.TrackerRepository;
import com.web.guitarapp.dao.UserRepository;
import com.web.guitarapp.entities.Course;
import com.web.guitarapp.entities.Tracker;
import com.web.guitarapp.entities.User;
import com.web.guitarapp.exception.ApiException;
import com.web.guitarapp.helper.FileUploadUtil;
import com.web.guitarapp.helper.Message;
import com.web.guitarapp.service.TrackerService;
import com.web.guitarapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import static com.web.guitarapp.util.Constants.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

  private final UserService userService;

  private final TrackerService trackerService;

  private final UserRepository userRepository;

  private final TrackerRepository trackerRepository;

  private final JavaMailSender mailSender;

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
      @Valid @ModelAttribute("user")@RequestParam("image_url") MultipartFile multipartFile, User user,
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
      String imageName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
      user.setImageUrl(imageName);
      userService.save(user);
      String imageDir = "user-images/" + user.getId();
      FileUploadUtil.saveFile(imageDir, imageName, multipartFile);
      Course course = new Course(1,"Beginner",1);
      Tracker tracker = new Tracker(course, user, 1, "ACTIVE");
      trackerService.save(tracker);

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
    model.addAttribute("user", user);
    return "dashboard";
  }

  @GetMapping("/beginnerLevel")
  public String beginnerLevel(Model model, Principal principal){
    String username = principal.getName();
    User user = userRepository.getUserByUserName(username);
    Optional<Tracker> optionalTracker = trackerRepository.findByUserIdAndStatus(user, "ACTIVE");

    int sub_level = optionalTracker.get().getSubLevel();
    System.out.println(sub_level);
    model.addAttribute("sublevel", sub_level);
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

  @PostMapping("/sendContact")
  public String sendContact(HttpServletRequest request){
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String subject = request.getParameter("subject");
    String message = request.getParameter("message");

    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setFrom("praweshgautam26@gmail.com");
    simpleMailMessage.setTo("guitarica99@gmail.com");

    String subjectOfMail = name + " has sent a message";
    String contentOfMail = "Sender Name: " + name +"\n";
    contentOfMail += "Sender Email: " + email + "\n";
    contentOfMail += "Subject: " + subject+ "\n";
    contentOfMail += "Message: " + message+ "\n";

    simpleMailMessage.setSubject(subjectOfMail);
    simpleMailMessage.setText(contentOfMail);

    mailSender.send(simpleMailMessage);

    return "contactMessage";
  }
  }

