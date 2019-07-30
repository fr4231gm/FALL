package forms;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;
import domain.DomainEntity;

public class ActorForm extends DomainEntity {

	// Atributos ----------------------------------------
	private String name;
	private String middleName;
	private String surname;
	private String photo;
	private String email;
	private String phoneNumber;
	private String address;
	private Double score;
	private UserAccount useraccount;
	private String username;
	private String password;
	private String passwordConfirmation;

	private String holder;
	private String make;
	private String number;
	private Integer expirationMonth;
	private Integer expirationYear;
	private Integer CVV;

	// Getters -----------------------------------------------------------------

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getMiddleName() {
		return this.middleName;
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
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@NotNull
	public UserAccount getUserAccount() {
		return this.useraccount;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	@Size(min = 5, max = 32)
	public String getPasswordConfirmation() {
		return this.passwordConfirmation;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getUsername() {
		return this.username;
	}

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z������������ ]*$")
	@Size(min = 1, max = 70)
	public String getHolder() {
		return this.holder;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getMake() {
		return this.make;
	}

	@Pattern(regexp = "^([0-9])*$")
	@NotBlank
	@CreditCardNumber
	public String getNumber() {
		return this.number;
	}

	@NotNull
	@Range(min = 1, max = 12)
	public Integer getExpirationMonth() {
		return this.expirationMonth;
	}

	@NotNull
	@Range(min = 19, max = 99)
	public Integer getExpirationYear() {
		return this.expirationYear;
	}

	@NotNull
	@Range(min = 100, max = 999)
	public Integer getCVV() {
		return this.CVV;
	}

	// Setters -----------------------------------------------------------------
	public void setName(final String name) {
		this.name = name;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
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

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setUserAccount(final UserAccount useraccount) {
		this.useraccount = useraccount;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setPasswordConfirmation(final String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public void setCVV(final Integer cVV) {
		this.CVV = cVV;
	}

	public void setExpirationYear(final Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	public void setExpirationMonth(final Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public void setMake(final String make) {
		this.make = make;
	}

	public void setHolder(final String holder) {
		this.holder = holder;
	}

}
