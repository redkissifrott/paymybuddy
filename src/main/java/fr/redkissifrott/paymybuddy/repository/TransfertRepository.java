/**
 * 
 */
package fr.redkissifrott.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.redkissifrott.paymybuddy.model.Transfer;

@Repository
public interface TransfertRepository extends CrudRepository<Transfer, Integer> {

}
