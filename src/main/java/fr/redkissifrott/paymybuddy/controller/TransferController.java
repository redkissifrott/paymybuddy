package fr.redkissifrott.paymybuddy.controller;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.redkissifrott.paymybuddy.customUser.CustomUserDetails;
import fr.redkissifrott.paymybuddy.model.User;
import fr.redkissifrott.paymybuddy.service.UserService;

@Controller
public class TransferController {

	@Autowired
	private UserService userService;

	private final Logger logger = LoggerFactory
			.getLogger(TransferController.class);

	@GetMapping("/transfer")
	public String transfer(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			Model model) {
		User user = userService.getUser(customUserDetails.getId()).get();
		// User user = userService.getUser(userId).get();
		model.addAttribute("user", user);
		model.addAttribute("friend", new User());
		return "transfer";
	}

	@Transactional
	@RequestMapping("/addFriend")
	public ModelAndView saveFriend(Model model,
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@RequestParam(value = "email") String email) {
		logger.info("RENVOI :{}", email);
		User user = userService.getUser(customUserDetails.getId()).get();
		logger.info("user :{}", user.getFirstName());
		User friend = userService.getUserByEmail(email);
		logger.info("friend :{}", friend.getFirstName());
		user.addFriend(friend);
		return new ModelAndView("redirect:/transfer");
	}

}
