package fr.redkissifrott.paymybuddy.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.redkissifrott.paymybuddy.model.Bank;
import fr.redkissifrott.paymybuddy.model.BankTransfer;
import fr.redkissifrott.paymybuddy.repository.BankRepository;
import fr.redkissifrott.paymybuddy.repository.BankTransferRepository;

@Service
public class BankService {

	@Autowired
	BankRepository bankRepository;

	@Autowired
	BankTransferRepository bankTransferRepository;

	public Bank getBank(String iban) {
		Bank bank = bankRepository.findByIban(iban);
		return bank;
	}

	public void deleteBank(String iban) {
		bankRepository.deleteByIban(iban);
	}

	final private ArrayList<BankTransfer> bankTransfers(Bank bank) {
		return bankTransferRepository.findAllByBankOrderByIdDesc(bank);
	}

	public Page<BankTransfer> findPaginated(Bank bank, Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<BankTransfer> list;

		if (bankTransfers(bank).size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize,
					bankTransfers(bank).size());
			list = bankTransfers(bank).subList(startItem, toIndex);
		}
		Page<BankTransfer> bankTransferPage = new PageImpl<BankTransfer>(list,
				PageRequest.of(currentPage, pageSize),
				bankTransfers(bank).size());

		return bankTransferPage;
	}
}
