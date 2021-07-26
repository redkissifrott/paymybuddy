package fr.redkissifrott.paymybuddy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import fr.redkissifrott.paymybuddy.model.User;
import fr.redkissifrott.paymybuddy.service.UserService;

@Controller
public class UsersController {

	@Autowired
	private UserService userService;

	public String listUsers(Model model) {
		List<User> listUsers = (List<User>) userService.getUsers();
		model.addAttribute("listUsers", listUsers);

		return "users";
	}

}
