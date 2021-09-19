package fr.redkissifrott.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.redkissifrott.paymybuddy.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findUserByEmail(String email);

}
