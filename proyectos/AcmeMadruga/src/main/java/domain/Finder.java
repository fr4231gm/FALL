
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

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
// para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class Finder extends DomainEntity {

	//Atributos
	private String	keyWord;
	private String	area;		// Sera el nombre del area. Aumenta la eficiencia.
	private Date	startDate;
	private Date	endDate;
	private Date	lastUpdate;


	//Constructor
	public Finder() {
		super();
	}

	//Getters
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getKeyWord() {
		return this.keyWord;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getArea() {
		return this.area;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}

	@Past
	// debe ser anterior a hoy
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	//Setters
	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	public void setArea(final String area) {
		this.area = area;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	//Relaciones
	private Collection<Procession>	processions;


	// Getters
	@ManyToMany
	public Collection<Procession> getProcessions() {
		return this.processions;
	}

	// Setters
	public void setProcessions(final Collection<Procession> processions) {
		this.processions = processions;
	}
}
