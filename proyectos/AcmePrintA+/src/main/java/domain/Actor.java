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
public abstract class Actor extends DomainEntity{

	// Attributes -------------------------------------------------------------
	private String name;
	private String middleName;
	private String surname;
	private String vatNumber;
	private String photo;
	private String email;
	private String phoneNumber;
	private String address;
	private boolean isSpammer;
	//private Double	polarity;
	
	// Constructor ------------------------------------------------------------
	public Actor() {
		super();
	}
	
	// Getters ----------------------------------------------------------------
	
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getName() {
		return this.name;
	}
	
	//@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getMiddleName() {
		return this.middleName;
	}
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
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

	@NotBlank
	public String getEmail() {
		return this.email;
	}
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public boolean getIsSpammer() {
		return this.isSpammer;
	}
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getAddress() {
		return this.address;
	}
	

	// Setters ----------------------------------------------------------------
	public void setName(final String name) {
		this.name = name;
	}
	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}
	public void setAddress(final String address) {
		this.address = address;
	}
	public void setPhone(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setIsSpammer(boolean isSpammer) {
		this.isSpammer = isSpammer;
	}

	// Relationships ----------------------------------------------------------
	
	// Atributes --------------------------------------------------------------
	
	private UserAccount userAccount;
	
	// Getters ----------------------------------------------------------------
	
	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}
	
	// Setters ----------------------------------------------------------------
	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	// toString ---------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Actor [name=" + name + ", middleName=" + middleName
				+ ", surname=" + surname + ", vatNumber=" + vatNumber
				+ ", photo=" + photo + ", email=" + email + ", phoneNumber="
				+ phoneNumber + ", address=" + address + ", isSpammer="
				+ isSpammer + ", userAccount=" + userAccount + "]";
	}
	
	
}
