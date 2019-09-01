
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Phase extends DomainEntity {

	// Atributos --------------------------------------------------------------
	private Integer	number;
	private Date	moment;
	private String	name;
	private String	comments;
	private Boolean	isDone;
	private Boolean	isDoneable;


	// Constructor ------------------------------------------------------------
	public Phase() {
		super();
	}

	// Getters ----------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	//@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getComments() {
		return this.comments;
	}

	@NotNull
	public Integer getNumber() {
		return this.number;
	}

	public Boolean getIsDone() {
		return this.isDone;
	}

	// Setters ----------------------------------------------------------------
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setNumber(final Integer number) {
		this.number = number;
	}

	public void setIsDone(final Boolean isDone) {
		this.isDone = isDone;
	}

	public Boolean getIsDoneable() {
		return this.isDoneable;
	}

	public void setIsDoneable(final Boolean isDoneable) {
		this.isDoneable = isDoneable;
	}
}
