
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	private String		comments;
	private Date		moment;
	private Endorser	creator;
	private Endorser	endorsed;


	@NotBlank
	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@ManyToOne(optional=false) 
	public Endorser getCreator() {
		return this.creator;
	}

	public void setCreator(final Endorser creator) {
		this.creator = creator;
	}

	@ManyToOne(optional=false)
	public Endorser getEndorsed() {
		return this.endorsed;
	}

	public void setEndorsed(final Endorser endorsed) {
		this.endorsed = endorsed;
	}
}
