package com.web.guitarapp.controller;

import com.web.guitarapp.dao.UserRepository;
import com.web.guitarapp.entities.User;
import com.web.guitarapp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Random;

import static com.web.guitarapp.util.Constants.LOGIN;

@Controller
public class ForgotController {
    Random random = new Random(1000);

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/forgot")
    public String openForm(){
        return "forgot_form";
    }

    @PostMapping("/sendOTP")
    public String sendOTP(@RequestParam ("email") String email, HttpSession session)
    {
    System.out.println("Email is: " + email);
    int otprequired = random.nextInt(9999999);
    System.out.println("OTP is: " + otprequired);
    String subject = "OTP To change the password ";
            String message ="" +
                    " <div style: 'border: 2px solid black; padding:20px'>" +
                    "<h1>" +
                    "OTP is:" +
                    "<b>" + otprequired +
                    "</b>" +
                    "</h1>" +
                    "</div>";
            String to = email;
    boolean flag = this.emailService.sendEmail(subject, message, to);

    if (flag){
        session.setAttribute("gototp", otprequired);
        session.setAttribute("email", email);

        return "otp_verify";
    }else{
        session.setAttribute("message", "Please check your email id: ");
        return "forgot_form";
    }
    }

    @PostMapping("/otp-verify")
    public String verifyOTP(@RequestParam ("otp") int otp, HttpSession session){
        int gotOTP = (int)session.getAttribute("gototp");
        String email = (String)session.getAttribute("email");

        if (gotOTP == otp){
            User user = this.userRepository.getUserByUserName(email);
            if (user == null){
                session.setAttribute("message", "User doesn't exist with the given Email: ");
                return "forgot_form";
      }
            else {
        return "change_password_form";
}
        }
        else {
            session.setAttribute("message", "You have wrong OTP code");
            return "otp_verify";
        }
    }
    @PostMapping("/password-change")
    public String passwordChange(@RequestParam("newpassword") String newpassword, HttpSession session){

        String email = (String)session.getAttribute("email");
        User user = this.userRepository.getUserByUserName(email);
        user.setPassword(this.bCryptPasswordEncoder.encode(newpassword));
        this.userRepository.save(user);

        return "redirect:/" +LOGIN+"?change=password changed successful";
    }
}
