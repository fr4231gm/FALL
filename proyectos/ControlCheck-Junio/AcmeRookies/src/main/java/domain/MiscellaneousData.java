
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;



import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
// para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class MiscellaneousData extends DomainEntity {

	//Atributos
	private String	text;
	private String	attachments;


	// Constructor2 -- vacio por defecto
	public MiscellaneousData() {
		super();
	}

	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getText() {
		return this.text;
	}
	
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getAttachments() {
		return this.attachments;
	}


	// Setters
	public void setText(final String text) {
		this.text = text;
	}

	public void setAttachments(final String attachment) {
		this.attachments = attachment;
	}

}
