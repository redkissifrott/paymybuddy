package fr.redkissifrott.paymybuddy.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.redkissifrott.paymybuddy.customUser.CustomUserDetails;
import fr.redkissifrott.paymybuddy.exception.FriendNotFoundException;
import fr.redkissifrott.paymybuddy.exception.TransferException;
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
			Model model, FriendTransfer friendtransfer, String errorMessage,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		User user = userService.getUser(customUserDetails.getId()).get();
		model.addAttribute("transfer", new FriendTransfer());
		model.addAttribute("user", user);
		model.addAttribute("friend", new User());

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(3);
		Page<FriendTransfer> friendTransferPage = userService
				.findPaginated(user, PageRequest.of(currentPage - 1, pageSize));
		List<String> transactions = new ArrayList<>();
		for (FriendTransfer f : friendTransferPage) {
			logger.info("FRIENDTRANSFER : {}", f.getFriend().getFirstName());
			logger.info("FRIENDTRANSFER : {}", f.getDescription());
			String connection = f.getFriend().getFirstName() + " "
					+ f.getFriend().getLastName();
			String description = f.getDescription();
			String amount = Integer.toString(f.getAmount()) + "€";
			String charges = "";
			if (f.getCharges() != null) {
				charges = Double.toString(f.getCharges()) + "€";
			}
			String insert = "<td>" + connection + "</td>" + "<td>" + description
					+ "</td>" + "<td>" + amount + "</td>" + "<td>" + charges
					+ "</td>";
			transactions.add(insert);
		}
		model.addAttribute("transactions", transactions);
		model.addAttribute("friendTransferPage", friendTransferPage);
		int totalPages = friendTransferPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("currentPage", currentPage);
		}
		// List<String> transactions = new ArrayList<>();
		// for (FriendTransfer f : friendTransfers) {
		// logger.info("FRIENDTRANSFER : {}", f.getFriend().getFirstName());
		// logger.info("FRIENDTRANSFER : {}", f.getDescription());
		// String connection = f.getFriend().getFirstName() + " "
		// + f.getFriend().getLastName();
		// String description = f.getDescription();
		// String amount = Integer.toString(f.getAmount()) + "€";
		// String charges = "";
		// if (f.getCharges() != null) {
		// charges = Double.toString(f.getCharges()) + "€";
		// }
		// String insert = "<td>" + connection + "</td>" + "<td>" + description
		// + "</td>" + "<td>" + amount + "</td>" + "<td>" + charges
		// + "</td>";
		// transactions.add(insert);
		// }
		// model.addAttribute("transactions", transactions);
		return "transfer";
	}

	@Transactional
	@RequestMapping(value = "/addFriend/{email}")
	public ModelAndView saveFriend(Model model,
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@RequestParam(value = "email") String email)
			throws FriendNotFoundException {
		User user = userService.getUser(customUserDetails.getId()).get();
		User friend = userService.getUserByEmail(email);
		if (friend == null)
			throw new FriendNotFoundException("This email (" + email
					+ ") does not belong to a user of our app - You can search the list of users <a href='/users'>here</a>");
		logger.info("FRIENDTRANSFER : {}", friend.getFirstName());
		user.addFriend(friend);
		return new ModelAndView("redirect:/transfer");
	}

	// @Transactional
	@RequestMapping("/payFriend")
	public ModelAndView payFriend(Model model,
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@RequestParam Integer friendId,
			@RequestParam(value = "description") String description,
			@RequestParam(value = "amount") Integer amount)
			throws TransferException {
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

	@ExceptionHandler({TransferException.class})
	public ModelAndView transferErrorMessage(TransferException e,
			RedirectAttributes redirAttrs) {
		redirAttrs.addFlashAttribute("errorMessage", e.getMessage());
		logger.info("error : {}", e.getMessage());
		return new ModelAndView("redirect:/transfer");
	}

	@ExceptionHandler({FriendNotFoundException.class})
	public ModelAndView friendNotFoundMessage(FriendNotFoundException e,
			RedirectAttributes redirAttrs) {
		redirAttrs.addFlashAttribute("notFoundMessage", e.getMessage());
		logger.info("error : {}", e.getMessage());
		return new ModelAndView("redirect:/transfer");
	}

	// @Transactional
	// @RequestMapping("/payFriend")
	// public ModelAndView payFriend(
	// @AuthenticationPrincipal CustomUserDetails customUserDetails,
	// @RequestParam Integer friendId,
	// @RequestParam(value = "description") String description,
	// @RequestParam(value = "amount") Integer amount)
	// throws TransferException {
	// User user = userService.getUser(customUserDetails.getId()).get();
	// User friend = userService.getUser(friendId).get();
	// FriendTransfer friendTransfer = new FriendTransfer();
	// friendTransfer.setUser(user);
	// friendTransfer.setFriend(friend);
	// friendTransfer.setDescription(description);
	// friendTransfer.setAmount(amount);
	// friendTransfer.setDate(LocalDate.now());
	// transferService.saveFriendTransfer(friendTransfer);
	// return new ModelAndView("redirect:/transfer");
	// }
}