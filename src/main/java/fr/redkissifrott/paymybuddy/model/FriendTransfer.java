package fr.redkissifrott.paymybuddy.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "friend_transfer")
@PrimaryKeyJoinColumn(name = "transfer_id")
public class FriendTransfer extends Transfer {

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "friend_id")
	private User friend;

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}

}
