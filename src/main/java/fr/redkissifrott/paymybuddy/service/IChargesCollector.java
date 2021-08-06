package fr.redkissifrott.paymybuddy.service;

import org.springframework.stereotype.Service;

@Service
public interface IChargesCollector {

	void chargesCollector(double transferCharges);

}
