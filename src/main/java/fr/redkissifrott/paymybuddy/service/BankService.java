package fr.redkissifrott.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.redkissifrott.paymybuddy.model.Bank;
import fr.redkissifrott.paymybuddy.repository.BankRepository;

@Service
public class BankService {

	@Autowired
	BankRepository bankRepository;

	public Bank getBank(String iban) {
		Bank bank = bankRepository.findByIban(iban);
		return bank;
	}

	public void deleteBank(String iban) {
		bankRepository.deleteByIban(iban);
	}

//	public Bank saveBank(Bank bank) {
//		return bankRepository.save(bank);
//	}

}
