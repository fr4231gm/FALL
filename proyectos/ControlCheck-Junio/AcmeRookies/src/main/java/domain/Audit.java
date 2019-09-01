
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Audit extends DomainEntity {

	//Atributos ---------------------------------------------------------------
	private Date	moment;
	private String	text;
	private Double	score;
	private Boolean	isDraft;


	//Constructor -------------------------------------------------------------
	public Audit() {
		super();
	}

	//Getters -----------------------------------------------------------------

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getText() {
		return this.text;
	}

	@NotNull
	@Range(min = 0, max = 10)
	public Double getScore() {
		return this.score;
	}

	public Boolean getIsDraft() {
		return this.isDraft;
	}

	//Setters -----------------------------------------------------------------

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public void setScore(final Double score) {
		this.score = score;
	}

	public void setIsDraft(final Boolean isDraft) {
		this.isDraft = isDraft;
	}


	//Relations --------------------------------------------------------------------------

	private Auditor		auditor;
	private Position	position;


	//Getters ----------------------------------------------------------------------------

	@ManyToOne(optional = true)
	public Auditor getAuditor() {
		return this.auditor;
	}

	@ManyToOne(optional = true)
	public Position getPosition() {
		return this.position;
	}

	//Setters ----------------------------------------------------------------------------
	public void setAuditor(final Auditor auditor) {
		this.auditor = auditor;
	}
	public void setPosition(final Position position) {
		this.position = position;
	}

}
