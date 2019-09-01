
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String					keyWord;
	private Date					startDate, endDate, lastUpdate;
	private Double					priceMax;
	private Double					priceMin;
	private HandyWorker				handyWorker;
	private Warranty				warranty;
	private Category				category;
	private Collection<FixUpTask>	fixUpTasks;


	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@OneToOne(optional = false)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

	@OneToOne(optional = true)
	public Warranty getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final Warranty warranty) {
		this.warranty = warranty;
	}

	@OneToOne(optional = true)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@ManyToMany
	public Collection<FixUpTask> getFixUpTasks() {
		return this.fixUpTasks;
	}

	public void setFixUpTasks(final Collection<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}

	@Valid
	@AttributeOverrides({
		@AttributeOverride(name = "value", column = @Column(name = "minimumValue")), @AttributeOverride(name = "currency", column = @Column(name = "minimumCurrency"))
	})
	public Double getPriceMax() {
		return this.priceMax;
	}
	@AttributeOverrides({
		@AttributeOverride(name = "value", column = @Column(name = "maximumValue")), @AttributeOverride(name = "currency", column = @Column(name = "maximumCurrency"))
	})
	public void setPriceMax(final Double priceMax) {
		this.priceMax = priceMax;
	}

	@Valid
	public Double getPriceMin() {
		return this.priceMin;
	}

	public void setPriceMin(final Double priceMin) {
		this.priceMin = priceMin;
	}

}
