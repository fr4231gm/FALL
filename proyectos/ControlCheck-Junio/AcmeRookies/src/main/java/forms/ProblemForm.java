
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import domain.DomainEntity;

public class ProblemForm extends DomainEntity {

	//Atributos
	private int		id;
	private String	title;
	private String	statement;
	private String	hint;
	private String	attachments;
	private Boolean	isDraft;


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

	@Override
	public int getId() {
		return this.id;
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
	@Override
	public void setId(final int id) {
		this.id = id;
	}
}
