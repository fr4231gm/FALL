
package forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import domain.DomainEntity;

public class ActorForm extends DomainEntity {

	//Atributos ---------------------------------------------------------------
	private String	name;
	private String	surname;
	private String	middleName;
	private String	vatNumber;
	private String	photo;
	private String	email;
	private String	phoneNumber;
	private String	address;
	private String	username;
	private String	password;
	private String	passwordConfirmation;
	private boolean	checkTerms;


	//Getters -----------------------------------------------------------------

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

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@NotBlank
	@Pattern(regexp = "^((ES-?)?([0-9A-Z][0-9]{7}[A-Z])|([A-Z][0-9]{7}[0-9A-Z]))$")
	public String getVatNumber() {
		return this.vatNumber;
	}

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

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getAddress() {
		return this.address;
	}
	/*
	 * public String getRole() {
	 * return this.role;
	 * }
	 */
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

	public boolean getCheckTerms() {
		return this.checkTerms;
	}

	
	//Setters -----------------------------------------------------------------
	public void setName(final String name) {
		this.name = name;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
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
	 * public void setRole(final String role) {
	 * this.role = role;
	 * }
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	public void setPasswordConfirmation(final String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public void setCheckTerms(final boolean checkTerms) {
		this.checkTerms = checkTerms;
	}
	

}
