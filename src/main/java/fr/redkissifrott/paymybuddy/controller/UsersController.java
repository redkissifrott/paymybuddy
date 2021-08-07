package fr.redkissifrott.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.redkissifrott.paymybuddy.service.UserService;

@Controller
public class UsersController {

	@Autowired
	UserService userService;

	@GetMapping("/users")
	public String users(Model model) {
		model.addAttribute("listUsers", userService.getUsers());
		return "users";

	}

}
