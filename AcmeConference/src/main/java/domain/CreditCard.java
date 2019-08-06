
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Access(AccessType.PROPERTY)
@Embeddable
public class CreditCard {

	// Atributos
	private String	holder;
	private String	number;
	private Integer	expirationMonth;
	private Integer	expirationYear;
	private String	make;
	private Integer	cvv;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getHolder() {
		return this.holder;
	}

	@NotBlank
	@CreditCardNumber
	public String getNumber() {
		return this.number;
	}

	@Range(min = 1, max = 12)
	@NotNull
	public Integer getExpirationMonth() {
		return this.expirationMonth;
	}

	@Range(min = 1, max = 99)
	@NotNull
	public Integer getExpirationYear() {
		return this.expirationYear;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getMake() {
		return this.make;
	}

	@Range(min = 100, max = 999)
	@NotNull
	public Integer getCVV() {
		return this.cvv;
	}

	// Setters
	public void setHolder(final String holder) {
		this.holder = holder;
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

	public void setMake(final String make) {
		this.make = make;
	}

	public void setCVV(final Integer cvv) {
		this.cvv = cvv;
	}

}
