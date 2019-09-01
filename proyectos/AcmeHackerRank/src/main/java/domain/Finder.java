
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
// para indicar que se trata de una entidad persistente --import
// javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class Finder extends DomainEntity {

	// Atributos --------------------------------------------------------------
	private String	keyword;
	private Date	deadline;
	private Date	lastUpdate;
	private Double	minimumSalary;
	private Double	maximumSalary;


	// Constructor ------------------------------------------------------------
	public Finder() {
		super();
	}

	// Getters ----------------------------------------------------------------
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getKeyword() {
		return this.keyword;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeadline() {
		return this.deadline;
	}

	@Past
	// debe ser anterior a hoy
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	@Range(min = 0, max = 99999)
	public Double getMaximumSalary() {
		return this.maximumSalary;
	}

	@Range(min = 0)
	public Double getMinimumSalary() {
		return this.minimumSalary;
	}

	// Setters ----------------------------------------------------------------
	public void setKeyword(final String keyWord) {
		this.keyword = keyWord;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setMinimumSalary(final Double minimumSalary) {
		this.minimumSalary = minimumSalary;
	}

	public void setMaximumSalary(final Double maximumSalary) {
		this.maximumSalary = maximumSalary;
	}


	// Relaciones -------------------------------------------------------------

	private Collection<Position>	positions;


	// Getters ----------------------------------------------------------------

	@ManyToMany
	public Collection<Position> getPositions() {
		return this.positions;
	}

	// Setters ----------------------------------------------------------------

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

}
