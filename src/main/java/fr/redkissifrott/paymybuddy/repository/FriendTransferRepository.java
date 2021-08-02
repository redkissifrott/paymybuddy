package fr.redkissifrott.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.redkissifrott.paymybuddy.model.FriendTransfer;

@Repository
public interface FriendTransferRepository
		extends
			JpaRepository<FriendTransfer, Integer> {

}
