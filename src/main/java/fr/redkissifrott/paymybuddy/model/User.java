package fr.redkissifrott.paymybuddy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.hibernate.annotations.DynamicUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@DynamicUpdate
public class User {

	@Transient
	private final Logger logger = LoggerFactory.getLogger(User.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String email;

	private String password;

	// private String pseudo;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	private float balance;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Bank> banks = new ArrayList<>();

	public void addBank(Bank bank) {
		banks.add(bank);
		bank.setUser(this);
	}

	public void removeBank(Bank bank) {
		banks.remove(bank);
		// bank.setUser(null);
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "friendship", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
	private List<User> friends = new ArrayList<User>();

	// logger.info("addFriend :{}", j.getFirstName());
	// friends.add(friend);

	public void addFriend(User friend) {
		logger.info("friend :{}", friend.getFirstName());
		this.friends.add(friend);
	}

	@ManyToMany(mappedBy = "friends", cascade = CascadeType.ALL)
	private List<User> friendof = new ArrayList<User>();

	public void addFriendof(User user) {
		friendof.add(user);
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public List<User> getFriendof() {
		return friendof;
	}

	public void setFriendof(List<User> friendof) {
		this.friendof = friendof;
	}

	@Transactional
	public List<User> friendship() {
		List<User> friendship = new ArrayList<>();
		friendship.addAll(friends);
		friendship.addAll(friendof);
		return friendship;
	}

	public List<Bank> getBanks() {
		return banks;
	}

	public void setBanks(List<Bank> banks) {
		this.banks = banks;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// public String getPseudo() {
	// return pseudo;
	// }
	//
	// public void setPseudo(String pseudo) {
	// this.pseudo = pseudo;
	// }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
