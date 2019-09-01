
package domain;

import java.util.Date;

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
public class EducationData extends DomainEntity {

	//Atributos
	private String	degree;
	private String	institution;
	private Double	mark;
	private Date	startDate;
	private Date	endDate;


	// Constructor2 -- vacio por defecto
	public EducationData() {
		super();
	}

	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDegree() {
		return this.degree;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getInstitution() {
		return this.institution;
	}

	@Range(min = 0, max = 10)
	public Double getMark() {
		return this.mark;
	}

	@NotNull
	public Date getStartDate() {
		return this.startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	// Setters
	public void setDegree(final String degree) {
		this.degree = degree;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	public void setMark(final Double mark) {
		this.mark = mark;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

}
