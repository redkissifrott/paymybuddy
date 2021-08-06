package fr.redkissifrott.paymybuddy.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friend_transfer")
@PrimaryKeyJoinColumn(name = "transfer_id")
public class FriendTransfer extends Transfer {

	@ManyToOne(cascade = {CascadeType.MERGE,
			CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@JoinColumn(name = "friend_id")
	private User friend;

	public User getFriend() {
		return friend;
	}

	@Builder
	public FriendTransfer(int id, User user, String description, Integer amount,
			Double charges, LocalDate date, User friend) {
		super(id, user, description, amount, charges, date);
		this.friend = friend;
	}

}
