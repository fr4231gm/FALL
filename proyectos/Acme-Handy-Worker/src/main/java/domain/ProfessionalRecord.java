
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class ProfessionalRecord extends DomainEntity {

	private String	companyName, role, attachmentLink, comments;
	private Date	startWorking, endWorking;


	@NotBlank
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

	@NotBlank
	public String getRole() {
		return this.role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	@URL
	@NotBlank
	public String getAttachmentLink() {
		return this.attachmentLink;
	}

	public void setAttachmentLink(final String attachmentLink) {
		this.attachmentLink = attachmentLink;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartWorking() {
		return this.startWorking;
	}

	public void setStartWorking(final Date startWorking) {
		this.startWorking = startWorking;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndWorking() {
		return this.endWorking;
	}

	public void setEndWorking(final Date endWorking) {
		this.endWorking = endWorking;
	}
}
