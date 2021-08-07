package fr.redkissifrott.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {
	@GetMapping("/login.html")
	public String login() {
		return "login.html";
	}
}
