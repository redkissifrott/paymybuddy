package fr.redkissifrott.paymybuddy.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.redkissifrott.paymybuddy.model.FriendTransfer;
import fr.redkissifrott.paymybuddy.model.User;

@Repository
public interface FriendTransferRepository
		extends
			JpaRepository<FriendTransfer, Integer> {

	ArrayList<FriendTransfer> findAllByUser(User user);

	ArrayList<FriendTransfer> findAllByUserOrderByIdDesc(User user);

}
