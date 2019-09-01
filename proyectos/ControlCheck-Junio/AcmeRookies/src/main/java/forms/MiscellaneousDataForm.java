package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import domain.Curricula;
import domain.DomainEntity;

public class MiscellaneousDataForm extends DomainEntity {

	//Atributos
	private int id;
	private int version;
	private String	text;
	private String	attachments;
	private Curricula	curricula;

	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getText() {
		return this.text;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getVersion() {
		return this.version;
	}
	
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getAttachments() {
		return this.attachments;
	}
	
	public Curricula getCurricula() {
		return this.curricula;
	}

	// Setters
	public void setText(final String text) {
		this.text = text;
	}

	public void setAttachments(final String attachment) {
		this.attachments = attachment;
	}
	
	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	public void setVersion(final int version) {
		this.version = version;
	}

}	
