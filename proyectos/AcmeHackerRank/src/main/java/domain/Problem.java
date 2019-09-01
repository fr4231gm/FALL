
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
//@Table(name="`problem`")
public class Problem extends DomainEntity {

	//Atributos --------------------------------------------------------------------------
	private String	title;
	private String	statement;
	private String	hint;
	private String	attachments;
	private Boolean	isDraft;


	//Constructor ------------------------------------------------------------------------
	public Problem() {
		super();
	}

	//Getters ----------------------------------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getStatement() {
		return this.statement;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getHint() {
		return this.hint;
	}

	//Tratar como pictures
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getAttachments() {
		return this.attachments;
	}

	public Boolean getIsDraft() {
		return this.isDraft;
	}

	//Setters ----------------------------------------------------------------------------
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	public void setHint(final String hint) {
		this.hint = hint;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	public void setIsDraft(final Boolean isDraft) {
		this.isDraft = isDraft;
	}


	//Relations --------------------------------------------------------------------------

	private Company	company;


	//Getters ----------------------------------------------------------------------------

	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}

	//Setters ----------------------------------------------------------------------------
	public void setCompany(final Company company) {
		this.company = company;
	}

}
