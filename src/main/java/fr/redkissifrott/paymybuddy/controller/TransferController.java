package fr.redkissifrott.paymybuddy.controller;

import java.time.LocalDate;

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
import fr.redkissifrott.paymybuddy.model.FriendTransfer;
import fr.redkissifrott.paymybuddy.model.User;
import fr.redkissifrott.paymybuddy.service.TransferService;
import fr.redkissifrott.paymybuddy.service.UserService;

@Controller
public class TransferController {

	@Autowired
	private UserService userService;

	@Autowired
	private TransferService transferService;

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
		User user = userService.getUser(customUserDetails.getId()).get();
		User friend = userService.getUserByEmail(email);
		user.addFriend(friend);
		return new ModelAndView("redirect:/transfer");
	}

	@Transactional
	@RequestMapping("/payFriend")
	public ModelAndView payFriend(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@RequestParam Integer friendId,
			@RequestParam(value = "description") String description,
			@RequestParam(value = "amount") Float amount) {
		User user = userService.getUser(customUserDetails.getId()).get();
		User friend = userService.getUser(friendId).get();
		FriendTransfer friendTransfer = new FriendTransfer();
		friendTransfer.setUser(user);
		friendTransfer.setFriend(friend);
		friendTransfer.setDescription(description);
		friendTransfer.setAmount(amount);
		friendTransfer.setDate(LocalDate.now());
		transferService.saveFriendTransfer(friendTransfer);
		return new ModelAndView("redirect:/transfer");
	}
	// @Transactional
	// @RequestMapping("/payFriend")
	// public ModelAndView payFriend(
	// @AuthenticationPrincipal CustomUserDetails customUserDetails,
	// // @ModelAttribute User friend,
	// @RequestParam Integer friendId) {
	// User user = userService.getUser(customUserDetails.getId()).get();
	// logger.info("USER - FRIEND : {} - {}", user.getId(), friendId);
	// return new ModelAndView("redirect:/transfer");
	// }

	// @Transactional
	// @RequestMapping("/payFriend")
	// public ModelAndView payFriend(
	// @AuthenticationPrincipal CustomUserDetails customUserDetails,
	// @ModelAttribute FriendTransfer friendTransfer,
	// @RequestParam Integer friendId) {
	// User user = userService.getUser(customUserDetails.getId()).get();
	// User friend = userService.getUser(friendId).get();
	// friendTransfer.setUser(user);
	// friendTransfer.setFriend(friend);
	// transferService.saveFriendTransfer(friendTransfer);
	// return new ModelAndView("redirect:/transfer");
	// }
}