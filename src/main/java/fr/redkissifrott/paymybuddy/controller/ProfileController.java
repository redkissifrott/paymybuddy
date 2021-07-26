package fr.redkissifrott.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.redkissifrott.paymybuddy.customUser.CustomUserDetails;
import fr.redkissifrott.paymybuddy.model.Bank;
import fr.redkissifrott.paymybuddy.model.User;
import fr.redkissifrott.paymybuddy.service.BankService;
import fr.redkissifrott.paymybuddy.service.UserService;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private BankService bankService;

	// @Autowired
	// private CustomUserDetails customUserDetails;

	// private int userId = 1;

	@GetMapping("/profile")
	public String profile(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			Model model) {
		User user = userService.getUser(customUserDetails.getId()).get();
		// User user = userService.getUser(userId).get();
		model.addAttribute("user", user);
		model.addAttribute("bank", new Bank());
		return "profile";
	}

	@GetMapping("deleteUser")
	public ModelAndView deleteUser(
			@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		User user = userService.getUser(customUserDetails.getId()).get();
		userService.deleteUser(user.getId());
		// userService.deleteUser(userId);
		return new ModelAndView("redirect:/");
	}

	@PostMapping("/addBank")
	@Transactional
	public ModelAndView saveBank(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@ModelAttribute Bank bank) {
		User user = userService.getUser(customUserDetails.getId()).get();
		// User user = userService.getUser(userId).get();
		bank.setUser(userService.getUser(user.getId()).get());
		user.addBank(bank);
		return null;
	}

	@GetMapping("/profile/deleteBank/{iban}")
	public void deleteBank(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@PathVariable("iban") String iban) {
		User user = userService.getUser(customUserDetails.getId()).get();
		Bank bank = bankService.getBank("iban");
		System.out.println(bank.getName());
		user.removeBank(bank);
		bankService.deleteBank(iban);

	}

}
