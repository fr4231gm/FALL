
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "sponsor")
}, uniqueConstraints = {
	@javax.persistence.UniqueConstraint(columnNames = {
		"number"
	})
})
public class CreditCard extends DomainEntity {

	// Atributos
	private String	holder;
	private String	make;
	private String	number;
	private Integer	expirationMonth;
	private Integer	expirationYear;
	private Integer	CVV;


	// Constructor
	public CreditCard() {
		super();
	}

	// Getters
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z ]*$")
	@Size(min = 1, max = 70)
	public String getHolder() {
		return this.holder;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
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

	// Setters
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


	//Relaciones
	private Sponsor	sponsor;


	//Getters
	@ManyToOne(optional = false)
	public Sponsor getSponsor() {
		return this.sponsor;
	}

	//Setters
	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}
}
