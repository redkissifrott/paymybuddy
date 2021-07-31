package fr.redkissifrott.paymybuddy.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.redkissifrott.paymybuddy.model.User;
import fr.redkissifrott.paymybuddy.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	private final Logger logger = LoggerFactory.getLogger(UserService.class);

	public Optional<User> getUser(final int id) {
		Optional<User> user = userRepository.findById(id);
		return user;
	}

	public User getUserByEmail(String email) {
		User user = userRepository.findUserByEmail(email);
		return user;
	}

	public void deleteUser(final int id) {
		userRepository.deleteById(id);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}

	// @Modifying
	// @Query(value = DBConstants.SAVE_FRIEND, nativeQuery = true)
	// public void addFriend(int userId, int friendId) {
	// logger.info("VARIABLES :{} {}", userId, friendId);
	// }

}
