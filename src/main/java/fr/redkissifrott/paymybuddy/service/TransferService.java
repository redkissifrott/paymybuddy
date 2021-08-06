package fr.redkissifrott.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.redkissifrott.paymybuddy.constants.TransferCharges;
import fr.redkissifrott.paymybuddy.model.BankTransfer;
import fr.redkissifrott.paymybuddy.model.FriendTransfer;
import fr.redkissifrott.paymybuddy.model.User;
import fr.redkissifrott.paymybuddy.repository.BankTransferRepository;
import fr.redkissifrott.paymybuddy.repository.FriendTransferRepository;

@Service
public class TransferService {

	@Autowired
	FriendTransferRepository friendTransferRepository;

	@Autowired
	BankTransferRepository bankTransferRepository;

	@Transactional(rollbackFor = Exception.class)
	public FriendTransfer saveFriendTransfer(FriendTransfer friendTransfer) {
		User user = friendTransfer.getUser();
		User friend = friendTransfer.getFriend();
		Integer amount = friendTransfer.getAmount();
		double transferCharges = amount * TransferCharges.TRANSFER_CHARGES;
		user.setBalance(user.getBalance() - amount - transferCharges);
		friendTransfer.setCharges(transferCharges);
		friend.setBalance(friend.getBalance() + amount);
		return friendTransferRepository.save(friendTransfer);
	}

	@Transactional(rollbackFor = Exception.class)
	public BankTransfer saveBankTransfer(BankTransfer bankTransfer) {
		User user = bankTransfer.getUser();
		Integer amount = bankTransfer.getAmount();
		double transferCharges = 0;
		if (bankTransfer.getDescription().equals("withdrawal")) {
			transferCharges = amount * TransferCharges.TRANSFER_CHARGES;
			amount = bankTransfer.getAmount() * -1;
			bankTransfer.setAmount(amount);
			bankTransfer.setCharges(transferCharges);
		}
		user.setBalance(user.getBalance() + amount - transferCharges);
		return bankTransferRepository.save(bankTransfer);
	}

}
