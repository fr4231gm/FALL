
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	// Atributos
	private String		name;
	private String		middlename;
	private String		surname;
	private String		photo;
	private String		email;
	private String		address;
	private Double		score;
	private String		phonenumber;
	private UserAccount	useraccount;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getMiddlename() {
		return this.middlename;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSurname() {
		return this.surname;
	}

	@URL
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPhoto() {
		return this.photo;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getEmail() {
		return this.email;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getAddress() {
		return this.address;
	}

	@Range(min = 0, max = 1)
	public Double getScore() {
		return this.score;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPhonenumber() {
		return this.phonenumber;
	}

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@NotNull
	public UserAccount getUserAccount() {
		return this.useraccount;
	}

	// Setters
	public void setName(final String name) {
		this.name = name;
	}

	public void setMiddlename(final String middlename) {
		this.middlename = middlename;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public void setScore(final Double score) {
		this.score = score;
	}

	public void setPhonenumber(final String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public void setUserAccount(final UserAccount useraccount) {
		this.useraccount = useraccount;
	}

}
