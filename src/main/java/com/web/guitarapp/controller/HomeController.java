package com.web.guitarapp.controller;
import com.web.guitarapp.dao.UserRepository;
import com.web.guitarapp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class HomeController {

private final UserRepository userRepository;

@Autowired
	public HomeController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping( "/home")
	public String base() {
		return "home";
	}
	@GetMapping( "/signup")
	public String signup(Model model) {
		model.addAttribute("title", "SignUp - Guitarica");
		model.addAttribute("user", new User());
		return "registration";
	}

	@RequestMapping(value = "/doRegister", method = RequestMethod.POST)
	public String signupUser(@ModelAttribute("user") User user, @RequestParam(value="agreement", defaultValue="false") boolean agreement,Model model)
	{

		if (!agreement){
			System.out.println("You have not agreed the terms and conditions.");
		}
		user.setRole("USER");
		user.setEnabled(true);
		System.out.println("Agreement: " + agreement);
		System.out.println("User" + user);
		User saveUser = this.userRepository.save(user);
		model.addAttribute("user", saveUser);
		return "registration";
	}


}

