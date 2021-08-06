package fr.redkissifrott.paymybuddy.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.redkissifrott.paymybuddy.model.Bank;
import fr.redkissifrott.paymybuddy.model.BankTransfer;

@Repository
public interface BankTransferRepository
		extends
			CrudRepository<BankTransfer, Integer> {

	ArrayList<BankTransfer> findByBank(Bank bank);

}
