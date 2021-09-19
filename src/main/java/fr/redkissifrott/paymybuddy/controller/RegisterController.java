package fr.redkissifrott.paymybuddy.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.redkissifrott.paymybuddy.exception.EmailExistsException;
import fr.redkissifrott.paymybuddy.model.User;
import fr.redkissifrott.paymybuddy.service.UserService;

@Controller
public class RegisterController {

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/addUser")
	@Transactional
	public ModelAndView saveUser(@ModelAttribute User user)
			throws EmailExistsException {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userService.saveUser(user);
		return new ModelAndView("redirect:/login");
	}

	@ExceptionHandler({EmailExistsException.class})
	public ModelAndView transferErrorMessage(EmailExistsException e,
			RedirectAttributes redirAttrs) {
		redirAttrs.addFlashAttribute("emailExistsMessage", e.getMessage());
		// logger.info("error : {}", e.getMessage());
		return new ModelAndView("redirect:/register");
	}

}
