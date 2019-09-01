
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
// para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class PeriodRecord extends DomainEntity {

	//Atributos
	private String	title;
	private String	description;
	private String	pictures;
	private Integer	startYear;
	private Integer	endYear;


	// Constructor2 -- vacio por defecto
	public PeriodRecord() {
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

	public String getPictures() {
		return this.pictures;
	}

	@NotNull
	@Range(min = 2019)
	public Integer getStartYear() {
		return this.startYear;
	}

	public Integer getEndYear() {
		return this.endYear;
	}

	// Setters
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setEndYear(final Integer endYear) {
		this.endYear = endYear;
	}

	public void setStartYear(final Integer startYear) {
		this.startYear = startYear;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

}
