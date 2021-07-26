package fr.redkissifrott.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;

import fr.redkissifrott.paymybuddy.model.Bank;

public interface BankRepository extends CrudRepository<Bank, String> {

	Bank findByIban(String iban);

	void deleteByIban(String iban);

}
