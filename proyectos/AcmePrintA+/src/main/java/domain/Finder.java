
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
// para indicar que se trata de una entidad persistente --import
// javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class Finder extends DomainEntity {

	// Atributos --------------------------------------------------------------
	private String	keyword;
	private Date	minDate;
	private Date	maxDate;
	private Date	lastUpdate;


	// Constructor ------------------------------------------------------------
	public Finder() {
		super();
	}

	// Getters ----------------------------------------------------------------
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getKeyword() {
		return this.keyword;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMinDate() {
		return this.minDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMaxDate() {
		return this.maxDate;
	}

	@Past
	// debe ser anterior a hoy
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	// Setters ----------------------------------------------------------------
	public void setKeyword(final String keyWord) {
		this.keyword = keyWord;
	}

	public void setMinDate(final Date minDate) {
		this.minDate = minDate;
	}

	public void setMaxDate(final Date maxDate) {
		this.maxDate = maxDate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	// Relaciones -------------------------------------------------------------

	private Collection<Order>	orders;

	// Getters ----------------------------------------------------------------

	@ManyToMany
	public Collection<Order> getOrders() {
		return this.orders;
	}

	// Setters ----------------------------------------------------------------

	public void setOrders(final Collection<Order> orders) {
		this.orders = orders;
	}

	// toString ---------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Finder [keyword=" + keyword + ", minDate=" + minDate
				+ ", maxDate=" + maxDate + ", lastUpdate=" + lastUpdate
				+ ", orders=" + orders + "]";
	}

}
