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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

import static com.web.guitarapp.util.Constants.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController { //making controller

  private final UserService userService; //

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
    Course course = trackerRepository.findByUserIdAndStatus(user, "ACTIVE").get().getCourseId();
    int subLevel = trackerRepository.findByUserIdAndStatus(user, "ACTIVE").get().getSubLevel();
    System.out.println(course.getWeightage());
    model.addAttribute("user", user);
    model.addAttribute("course", course);
    model.addAttribute("sublevel", subLevel);
    return "dashboard";
  }

  @RequestMapping("/nextLevel")
  public String insertData(Principal principal){
    String username = principal.getName();
    System.out.println(username);
    User user = userRepository.getUserByUserName(username);
    if (trackerRepository.findByUserIdAndStatus(user, "ACTIVE").isPresent()) {
      trackerRepository.findByUserIdAndStatus(user, "ACTIVE").get().setStatus("COMPLETED");
    }
    Course course = new Course(1,"Beginner",1);
    Tracker tracker = new Tracker(course, user, trackerRepository.findByUserIdAndStatus(user,"ACTIVE").get().getSubLevel() + 1, "ACTIVE");
    trackerRepository.save(tracker);

    return "redirect:/beginnerLevel";
  }
  @RequestMapping("/backLevel")
  public String insertDataBack(Principal principal){
    String username = principal.getName();
    System.out.println(username);
    User user = userRepository.getUserByUserName(username);
    if (trackerRepository.findByUserIdAndStatus(user, "ACTIVE").isPresent()) {
      trackerRepository.findByUserIdAndStatus(user, "ACTIVE").get().setStatus("COMPLETED");
    }
    Course course = new Course(1,"Beginner",1);
    Tracker tracker = new Tracker(course, user, trackerRepository.findByUserIdAndStatus(user,"ACTIVE").get().getSubLevel() - 1, "ACTIVE");
    trackerRepository.save(tracker);

    return "redirect:/beginnerLevel";
  }

  @RequestMapping("/nextLevelInter")
  public String insertDataInter(Principal principal){
    String username = principal.getName();
    User user = userRepository.getUserByUserName(username);
    if (trackerRepository.findByUserIdAndStatus(user, "ACTIVE").isPresent()) {
      trackerRepository.findByUserIdAndStatus(user, "ACTIVE").get().setStatus("COMPLETED");
    }
    Course course = new Course(2,"Intermediate",2);
    Tracker tracker = new Tracker(course, user, trackerRepository.findByUserIdAndStatus(user,"ACTIVE").get().getSubLevel() + 1, "ACTIVE");
    trackerRepository.save(tracker);

    return "redirect:/intermediateLevel";
  }
  @RequestMapping("/backLevelIntermediate")
  public String insertDataBackInter(Principal principal){
    String username = principal.getName();
    User user = userRepository.getUserByUserName(username);
    if (trackerRepository.findByUserIdAndStatus(user, "ACTIVE").isPresent()) {
      trackerRepository.findByUserIdAndStatus(user, "ACTIVE").get().setStatus("COMPLETED");
    }
    Course course = new Course(2,"Intermediate",2);
    Tracker tracker = new Tracker(course, user, trackerRepository.findByUserIdAndStatus(user,"ACTIVE").get().getSubLevel() - 1, "ACTIVE");
    trackerRepository.save(tracker);

    return "redirect:/intermediateLevel";
  }
  @RequestMapping("/backLevelAdvance")
  public String insertDataBackAdvance(Principal principal){
    String username = principal.getName();
    User user = userRepository.getUserByUserName(username);
    if (trackerRepository.findByUserIdAndStatus(user, "ACTIVE").isPresent()) {
      trackerRepository.findByUserIdAndStatus(user, "ACTIVE").get().setStatus("COMPLETED");
    }
    Course course = new Course(3,"Advance",3);
    Tracker tracker = new Tracker(course, user, trackerRepository.findByUserIdAndStatus(user,"ACTIVE").get().getSubLevel() - 1, "ACTIVE");
    trackerRepository.save(tracker);

    return "redirect:/advanceLevel";
  }
  @RequestMapping("/nextLevelAdvance")
  public String insertDataAdvance(Principal principal){
    String username = principal.getName();
    User user = userRepository.getUserByUserName(username);
    if (trackerRepository.findByUserIdAndStatus(user, "ACTIVE").isPresent()) {
      trackerRepository.findByUserIdAndStatus(user, "ACTIVE").get().setStatus("COMPLETED");
    }
    Course course = new Course(3,"Advance",3);
    Tracker tracker = new Tracker(course, user, trackerRepository.findByUserIdAndStatus(user,"ACTIVE").get().getSubLevel() + 1, "ACTIVE");
    trackerRepository.save(tracker);

    return "redirect:/advanceLevel";
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
  @GetMapping("/intermediateLevel")
  public String intermediateLevel(Model model, Principal principal){
    String usernamefind = principal.getName();
    User userfind = userRepository.getUserByUserName(usernamefind);
    Optional<Tracker> optionalTracker = trackerRepository.findByUserIdAndStatus(userfind, "ACTIVE");

    int sub_level = optionalTracker.get().getSubLevel();
    System.out.println(sub_level);
    model.addAttribute("sub_level", sub_level);

    return "IntermediateLevel";
  }

  @GetMapping("/advanceLevel")
  public String advanceLevel(Model model, Principal principal){
    String usernamefind = principal.getName();
    User userfind = userRepository.getUserByUserName(usernamefind);
    Optional<Tracker> optionalTracker = trackerRepository.findByUserIdAndStatus(userfind, "ACTIVE");

    int sub_level = optionalTracker.get().getSubLevel();
    System.out.println(sub_level);
    model.addAttribute("sublevel", sub_level);

    return "AdvanceLevel";
  }

  @GetMapping("/beginnerComplete")
  public String beginnerFinish(Model model, Principal principal){
    String username = principal.getName();
    Course course = new Course(2,"Intermediate",2);
    User user = userRepository.getUserByUserName(username);
    if (trackerRepository.findByUserIdAndStatus(user, "ACTIVE").isPresent()) {
      trackerRepository.findByUserIdAndStatus(user, "ACTIVE").get().setStatus("COMPLETED");
    }
    Tracker tracker = new Tracker(course, user, 6, "ACTIVE");
    trackerService.save(tracker);

    return "BeginnerLevel/beginnerComplete";
  }
  @GetMapping("/intermediateComplete")
  public String intermediateFinish(Model model, Principal principal){
    String username = principal.getName();
    Course course = new Course(3,"Advance",3);
    User user = userRepository.getUserByUserName(username);
    if (trackerRepository.findByUserIdAndStatus(user, "ACTIVE").isPresent()) {
      trackerRepository.findByUserIdAndStatus(user, "ACTIVE").get().setStatus("COMPLETED");
    }
    Tracker tracker = new Tracker(course, user, 11, "ACTIVE");
    trackerService.save(tracker);

    return "IntermediateLevel/intermediateComplete";
  }

  @GetMapping("/guitartuner")
  public String guitartuner(){
    return "tuner";
  }

  @GetMapping("/completed")
  public String completed( Model model,Principal principal){
    String username = principal.getName();
    User userfind = userRepository.getUserByUserName(username);
    String userName = userfind.getUsername();
    System.out.println(username);
    Course course = new Course(3,"Advance",3);
    User user = userRepository.getUserByUserName(username);
    if (trackerRepository.findByUserIdAndStatus(user, "ACTIVE").isPresent()) {
      trackerRepository.findByUserIdAndStatus(user, "ACTIVE").get().setStatus("COMPLETED");
    }
    Tracker tracker = new Tracker(course, user, 16, "ACTIVE");
    trackerService.save(tracker);
    model.addAttribute("userName", userName);
    return "certificate";
  }

  @GetMapping("/chatbox")
  public String chatbox(Model model, Principal principal){
    String username = principal.getName();
    User userfind = userRepository.getUserByUserName(username);
    String userName = userfind.getUsername();
    model.addAttribute("userName", userName);
    return "ChatMessage";
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

