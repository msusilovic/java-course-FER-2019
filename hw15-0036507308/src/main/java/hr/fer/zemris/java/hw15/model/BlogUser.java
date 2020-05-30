package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Model of some blog user to be stored in a database.
 * 
 * @author Martina
 *
 */
@Entity
@Table(name="blog_users")
public class BlogUser {

	/**
	 * @return the entries
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * @param entries the entries to set
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	/**
	 * User id.
	 */
	private Long id;
	
	/**
	 * User's name.
	 */
	private String firstName;
	
	/**
	 * User's last name.
	 */
	private String lastName;
	
	/**
	 * User's nickname.
	 */
	private String nick;
	
	/**
	 * User's email.
	 */
	private String email;
	
	/**
	 * Hash value for this user's password.
	 */
	private String passwordHash;
	
	/**
	 * List of entries for this user.
	 */
	private List<BlogEntry> entries = new ArrayList<>();
	
	/**
	 * Returns id.
	 * 
	 * @return the id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets id.
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Returns first name.
	 * 
	 * @return the firstName
	 */
	@Column(length=30,nullable=false)
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets first name.
	 * 
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Returns last name.
	 * 
	 * @return the lastName
	 */
	@Column(length=50,nullable=false)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets last name.
	 * 
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Returns nick.
	 * 
	 * @return the nick
	 */
	@Column(unique=true,length=20,nullable=false)
	public String getNick() {
		return nick;
	}
	
	/**
	 * Sets nick.
	 * 
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Returns email.
	 * 
	 * @return the email
	 */
	@Column(length=100,nullable=false)
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets email.
	 * 
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Returns password hash.
	 * 
	 * @return the passwordHash
	 */
	@Column(length=40,nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Sets password hash.
	 * 
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
