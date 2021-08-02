package fr.redkissifrott.paymybuddy.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "bank_transfer")
@PrimaryKeyJoinColumn(name = "transfer_id")
public class BankTransfer extends Transfer {

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	@ManyToOne
	@JoinColumn(name = "iban")
	private Bank bank;

}
