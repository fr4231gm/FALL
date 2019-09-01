
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	private String		refereeComments, handyworkerComments, customerComments;
	private Date		moment;


	public String getRefereeComments() {
		return this.refereeComments;
	}

	public void setRefereeComments(final String refereeComments) {
		this.refereeComments = refereeComments;
	}

	public String getHandyworkerComments() {
		return this.handyworkerComments;
	}

	public void setHandyworkerComments(final String handyworkercomments) {
		this.handyworkerComments = handyworkercomments;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public String getCustomerComments() {
		return customerComments;
	}

	public void setCustomerComments(String customerComments) {
		this.customerComments = customerComments;
	}
	
}
