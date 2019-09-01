
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
public class LegalRecord extends DomainEntity {

	//Atributos
	private String	title;
	private String	description;
	private String	legalName;
	private String	applicableLaws;
	private String	vatNumber;


	// Constructor2 -- vacio por defecto
	public LegalRecord() {
		super();
	}

	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}
	
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getLegalName() {
		return legalName;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getApplicableLaws() {
		return applicableLaws;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getVatNumber() {
		return vatNumber;
	}

	// Setters
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public void setApplicableLaws(String applicableLaws) {
		this.applicableLaws = applicableLaws;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

}
