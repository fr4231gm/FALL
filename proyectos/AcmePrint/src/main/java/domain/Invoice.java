
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@javax.persistence.UniqueConstraint(columnNames = {
		"application"
	})
})
public class Invoice extends DomainEntity {

	//Atributos----------------------------------------------------------------
	private Date	moment;
	private String	description;
	private Double	price;
	


	//Constructor--------------------------------------------------------------
	public Invoice() {
		super();
	}

	//Getters------------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	public Date getMoment() {
		return this.moment;
	}

	public String getDescription() {
		return this.description;
	}

	@Range(min = 0, max = 99999)
	public Double getPrice() {
		return this.price;
	}

	//Setters------------------------------------------------------------------

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}


	//Relaciones---------------------------------------------------------------
	private Application	application;


	//Getters------------------------------------------------------------------

	@OneToOne(optional = false)
	public Application getApplication() {
		return this.application;
	}

	//Setters------------------------------------------------------------------
	public void setApplication(final Application application) {
		this.application = application;
	}

	// toString ---------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Invoice [moment=" + moment + ", description=" + description
				+ ", price=" + price + ", application=" + application + "]";
	}

}
