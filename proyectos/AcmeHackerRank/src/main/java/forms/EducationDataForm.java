
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Curricula;
import domain.DomainEntity;

public class EducationDataForm extends DomainEntity {

	// Atributos
	private int			id;
	private int			version;
	private Curricula	curricula;
	private String		degree;
	private String		institution;
	private Double		mark;
	private Date		startDate;
	private Date		endDate;


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

	@NotNull
	@Range(min = 0, max = 10)
	public Double getMark() {
		return this.mark;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public int getVersion() {
		return this.version;
	}

	public Curricula getCurricula() {
		return this.curricula;
	}

	// Setters
	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;
	}

	@Override
	public void setId(final int id) {
		this.id = id;
	}

	@Override
	public void setVersion(final int version) {
		this.version = version;
	}

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
