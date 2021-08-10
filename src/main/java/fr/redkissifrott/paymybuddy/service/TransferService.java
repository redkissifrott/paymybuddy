package fr.redkissifrott.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.redkissifrott.paymybuddy.constants.TransferCharges;
import fr.redkissifrott.paymybuddy.exception.TransferException;
import fr.redkissifrott.paymybuddy.model.BankTransfer;
import fr.redkissifrott.paymybuddy.model.FriendTransfer;
import fr.redkissifrott.paymybuddy.model.User;
import fr.redkissifrott.paymybuddy.repository.BankTransferRepository;
import fr.redkissifrott.paymybuddy.repository.FriendTransferRepository;
import fr.redkissifrott.paymybuddy.repository.UserRepository;

@Service
public class TransferService {

	@Autowired
	FriendTransferRepository friendTransferRepository;

	@Autowired
	BankTransferRepository bankTransferRepository;

	@Autowired
	UserRepository userRepository;

	@Transactional(rollbackFor = TransferException.class)
	public FriendTransfer saveFriendTransfer(FriendTransfer friendTransfer)
			throws TransferException {
		User user = friendTransfer.getUser();
		User friend = friendTransfer.getFriend();
		Integer amount = friendTransfer.getAmount() * -1;
		Double transferCharges = balanceWithdrawal(user, amount);
		friend.setBalance(friend.getBalance() + amount * -1);
		friendTransfer.setCharges(transferCharges * -1);
		return friendTransferRepository.save(friendTransfer);
	}

	@Transactional(rollbackFor = TransferException.class)
	public BankTransfer saveBankTransfer(BankTransfer bankTransfer)
			throws TransferException {
		User user = bankTransfer.getUser();
		Integer amount = bankTransfer.getAmount();
		if (bankTransfer.getDescription().equals("withdrawal")) {
			amount = bankTransfer.getAmount() * -1;
			Double transferCharges = balanceWithdrawal(user, amount);
			bankTransfer.setAmount(amount);
			bankTransfer.setCharges(transferCharges);
		} else {
			user.setBalance(user.getBalance() + amount);
		}
		return bankTransferRepository.save(bankTransfer);
	}

	// @Transactional(propagation = Propagation.MANDATORY)
	public Double balanceWithdrawal(User user, Integer amount)
			throws TransferException {
		Double balance = user.getBalance();
		double transferCharges = amount * TransferCharges.TRANSFER_CHARGES;
		double newBalance = balance + amount + transferCharges;
		if (newBalance < 0) {
			throw new TransferException("Your account balance (" + balance
					+ "€) is insufficient for this transaction (" + amount * -1
					+ " + " + transferCharges * -1 + " = "
					+ ((amount * -1) + (transferCharges * -1)) + "€)");
		}
		user.setBalance(newBalance);
		return transferCharges;
	}

}
