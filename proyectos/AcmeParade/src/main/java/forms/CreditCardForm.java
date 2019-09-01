
package forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import domain.DomainEntity;

public class CreditCardForm extends DomainEntity {

	//Atributos --------------------------------------
	private int		id;
	private String	holder;
	private String	make;
	private String	number;
	private Integer	expirationMonth;
	private Integer	expirationYear;
	private Integer	CVV;
	private Integer	sponsorId;


	//Getters-----------------------------------------

	@Override
	public int getId() {
		return this.id;
	}

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z ]*$")
	@Size(min = 1, max = 70)
	public String getHolder() {
		return this.holder;
	}

	@NotBlank
	public String getMake() {
		return this.make;
	}

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

	public Integer getSponsorId() {
		return this.sponsorId;
	}
	//Setters-----------------------------------------
	@Override
	public void setId(final int id) {
		this.id = id;
	}

	public void setHolder(final String holder) {
		this.holder = holder;
	}

	public void setMake(final String make) {
		this.make = make;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public void setExpirationMonth(final Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public void setExpirationYear(final Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	public void setCVV(final Integer cVV) {
		this.CVV = cVV;
	}
	public void setSponsorId(final Integer sponsorId) {
		this.sponsorId = sponsorId;
	}

}
