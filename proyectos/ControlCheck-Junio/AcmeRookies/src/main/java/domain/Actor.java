
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "isSpammer")/*, @Index(columnList = "polarity")*/
})
public abstract class Actor extends DomainEntity {

	// Atributos -----------------
	private String	name;
	private String	surname;
	private String	vatNumber;
	private String	photo;
	private String	email;
	private String	phoneNumber;
	private String	address;
	//private Double	polarity;
	private boolean	isSpammer;


	// Constructor ---------------
	public Actor() {
		super();
	}

	// Getters -------------------
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@NotBlank
	public String getName() {
		return this.name;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@NotBlank
	@Pattern(regexp = "^((ES-?)?([0-9A-Z][0-9]{7}[A-Z])|([A-Z][0-9]{7}[0-9A-Z]))$")
	public String getVatNumber() {
		return this.vatNumber;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@URL
	public String getPhoto() {
		return this.photo;
	}

	// @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public boolean getIsSpammer() {
		return this.isSpammer;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getAddress() {
		return this.address;
	}

	/*@Range(min = -1, max = 1)
	public Double getPolarity() {
		return this.polarity;
	}*/

	// Setters -------------------
	public void setName(final String name) {
		this.name = name;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public void setVatNumber(final String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setAddress(final String address) {
		this.address = address;
	}
/*
	public void setPolarity(final Double polarity) {
		this.polarity = polarity;
	}*/

	public void setIsSpammer(final boolean spammer) {
		this.isSpammer = spammer;
	}


	// Relaciones -------------------
	private UserAccount	userAccount;


	// Getters ----------------------
	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	// Setters ----------------------
	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
