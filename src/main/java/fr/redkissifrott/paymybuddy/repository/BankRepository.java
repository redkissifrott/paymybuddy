package fr.redkissifrott.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.redkissifrott.paymybuddy.model.Bank;

public interface BankRepository extends JpaRepository<Bank, String> {

	public Bank findByIban(String iban);

	public Long deleteByIban(String iban);

}
