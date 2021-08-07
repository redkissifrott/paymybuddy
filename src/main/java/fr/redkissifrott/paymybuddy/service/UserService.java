package fr.redkissifrott.paymybuddy.service;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import fr.redkissifrott.paymybuddy.model.FriendTransfer;
import fr.redkissifrott.paymybuddy.model.User;
import fr.redkissifrott.paymybuddy.repository.FriendTransferRepository;
import fr.redkissifrott.paymybuddy.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	FriendTransferRepository friendTransferRepository;

	private final Logger logger = LoggerFactory.getLogger(UserService.class);

	public Optional<User> getUser(final int id) {
		Optional<User> user = userRepository.findById(id);
		return user;
	}

	public User getUserByEmail(String email) {
		User user = userRepository.findUserByEmail(email);
		return user;
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}

	public void logoutUser(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	}

	public ArrayList<FriendTransfer> friendTransfers(User user) {
		return friendTransferRepository.findByUser(user);
	}

	// @Transactional(rollbackFor = FriendNotFoundException.class)
	// public void addFriendService(User user, String email) {
	// List<User> userList = new ArrayList<>();
	// for (User u : getUsers()) {
	// userList.add(u);
	// }
	// if (!userList.contains(friend)) {
	// throw new FriendNotFoundException("This email ("+email+ ")does not belong
	// to a user of our app - You can search the list of users here")
	// }
	// user.addFriend(friend);
	// }

}
