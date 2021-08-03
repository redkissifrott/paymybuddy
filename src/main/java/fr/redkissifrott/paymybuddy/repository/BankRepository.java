package fr.redkissifrott.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;

import fr.redkissifrott.paymybuddy.model.Bank;

public interface BankRepository extends CrudRepository<Bank, String> {

	public Bank findByIban(String iban);

	public Long deleteByIban(String iban);

}
