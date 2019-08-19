
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	// Atributos
	private String					keyWord;
	private Date					startDate;
	private Date					endDate;
	private Double					fee;
	private Collection<Conference>	conferences;
	private Author					author;
	private Category				category;


	// Getters
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getKeyWord() {
		return this.keyWord;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}

	@Range(min = 0, max = 1)
	public Double getFee() {
		return this.fee;
	}

	@ManyToMany
	public Collection<Conference> getConferences() {
		return this.conferences;
	}

	// Setters
	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public void setendDate(final Date endDate) {
		this.endDate = endDate;
	}

	public void setFee(final Double fee) {
		this.fee = fee;
	}

	public void setConferences(final Collection<Conference> conferences) {
		this.conferences = conferences;
	}

	@OneToOne(optional = true)
	public Category getCategory() {
		return this.category;
	}
	public void setCategory(final Category category) {
		this.category = category;
	}

}
