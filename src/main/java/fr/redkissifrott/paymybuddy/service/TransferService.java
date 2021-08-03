package fr.redkissifrott.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.redkissifrott.paymybuddy.model.FriendTransfer;
import fr.redkissifrott.paymybuddy.repository.FriendTransferRepository;

@Service
public class TransferService {

	@Autowired
	FriendTransferRepository friendTransferRepository;

	public FriendTransfer saveFriendTransfer(FriendTransfer friendTransfer) {
		return friendTransferRepository.save(friendTransfer);
	}

}
