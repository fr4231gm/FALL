
package forms;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Curricula;
import domain.DomainEntity;

public class PositionDataForm extends DomainEntity {

	//Atributos
	private int					id;
	private int					version;
	private domain.Curricula	curricula;
	private String				title;
	private String				description;
	private Date				startDate;
	private Date				endDate;


	// Constructor2 -- vacio por defecto
	public PositionDataForm() {
		super();
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
	@Override
	public void setId(final int id) {
		this.id = id;
	}

	@Override
	public void setVersion(final int version) {
		this.version = version;
	}
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public domain.Curricula getCurricula() {
		return this.curricula;
	}

	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public int getVersion() {
		return this.version;
	}

}
