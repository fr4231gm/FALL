
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Charge extends DomainEntity {

	//Atributos----------------------------------------------------------------
	private Date	momment;
	private Double	amount;
	private Double	tax;


	//Constructor--------------------------------------------------------------
	public Charge() {
		super();
	}

	//Getters------------------------------------------------------------------
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMomment() {
		return this.momment;
	}

	@NotNull
	@Range(min = 0, max = 99999)
	public Double getAmount() {
		return this.amount;
	}

	@NotNull
	@Range(min = 0, max = 100)
	public Double getTax() {
		return this.tax;
	}

	//Setteres-----------------------------------------------------------------

	public void setMomment(final Date momment) {
		this.momment = momment;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	public void setTax(final Double tax) {
		this.tax = tax;
	}

}
