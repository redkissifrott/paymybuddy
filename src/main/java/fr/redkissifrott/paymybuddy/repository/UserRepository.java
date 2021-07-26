package fr.redkissifrott.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.redkissifrott.paymybuddy.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	public User findUserByEmail(String email);

}
