
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
// para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class PersonalData extends DomainEntity {

	//Atributos
	private String	fullName;
	private String	statement;
	private String	phoneNumber;
	private String	gitHubLink;
	private String	linkedInLink;


	// Constructor2 -- vacio por defecto
	public PersonalData() {
		super();
	}

	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getFullName() {
		return this.fullName;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getStatement() {
		return this.statement;
	}

	@NotBlank
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	@NotBlank
	@URL
	public String getLinkedInLink() {
		return this.linkedInLink;
	}

	@NotBlank
	@URL
	public String getGitHubLink() {
		return this.gitHubLink;
	}

	// Setters
	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setGitHubLink(final String gitHubLink) {
		this.gitHubLink = gitHubLink;
	}

	public void setLinkedInLink(final String linkedInLink) {
		this.linkedInLink = linkedInLink;
	}

}
