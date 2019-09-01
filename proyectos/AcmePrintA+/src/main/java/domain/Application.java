
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	// Atributos ---------------------------------------------------------------

	private Date	moment;
	private String	status;
	private Double	offeredPrice;
	private String	companyComments;
	private String	customerComments;


	// Constructor ------------------------------------------------------------
	public Application() {
		super();
	}

	// Getters ----------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@Pattern(regexp = "^PENDING|ACCEPTED|REJECTED$")
	public String getStatus() {
		return this.status;
	}

	@NotNull
	@Range(min = 0, max = 9999)
	public Double getOfferedPrice() {
		return this.offeredPrice;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getCompanyComments() {
		return this.companyComments;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getCustomerComments() {
		return this.customerComments;
	}

	// Setters ----------------------------------------------------------------
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public void setOfferedPrice(final Double offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	public void setCompanyComments(final String companyComments) {
		this.companyComments = companyComments;
	}

	public void setCustomerComments(final String customerComments) {
		this.customerComments = customerComments;
	}


	//Relaciones---------------------------------------------------------------

	private Order	order;
	private Company	company;


	//Getters------------------------------------------------------------------

	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}

	@ManyToOne(optional = false)
	public Order getOrder() {
		return this.order;
	}

	//Setters------------------------------------------------------------------

	public void setOrder(final Order order) {
		this.order = order;
	}
	public void setCompany(final Company company) {
		this.company = company;
	}

	// toString ---------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Application [moment=" + moment + ", status=" + status
				+ ", offeredPrice=" + offeredPrice + ", companyComments="
				+ companyComments + ", customerComments=" + customerComments
				+ ", order=" + order + ", company=" + company.getCommercialName() + "]";
	}

}
