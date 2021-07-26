package fr.redkissifrott.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.redkissifrott.paymybuddy.model.User;
import fr.redkissifrott.paymybuddy.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public Optional<User> getUser(final int id) {
		Optional<User> user = userRepository.findById(id);
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

}
