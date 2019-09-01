
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity // para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY) // la manera de acceder a la entidad--import javax.persistence.AccessType;
@Table(
		uniqueConstraints = 
		@javax.persistence.UniqueConstraint(columnNames={"ticker"})
		)
public class Procession extends DomainEntity {

	// Atributos
	private Date	moment;
	private String	ticker;
	private String	title;
	private String	description;
	private Boolean	isDraft;
	private Integer rows;
	private Collection<String> locations;


	// Constructor -- vacío por defecto
	public Procession() {
		super();
	}

	//Getters
	public Date getMoment() {
		return this.moment;
	}

	@NotBlank
	//corregida 03, funciona
	@Column(unique = true)
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTicker() {
		return this.ticker;
	}

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

	public Boolean getisDraft() {
		return this.isDraft;
	}
	@Range(min=1, max=5)
	public Integer getRows() {
		return rows;
	}
	
	@ElementCollection
	public Collection<String> getLocations() {
		return locations;
	}

	//Setters
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setIsDraft(final Boolean isDraft) {
		this.isDraft = isDraft;
	}
	
	// Relaciones
	private Brotherhood			brotherhood;
	private Collection<domain.Float>	floats;


	// Getters
	@ManyToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}
	
	@ManyToMany
	public Collection<domain.Float> getFloats() {
		return this.floats;
	}

	// Setters
	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}
	public void setFloats(final Collection<domain.Float> floats) {
		this.floats = floats;
	}

	public void setLocations(Collection<String> locations) {
		this.locations = locations;
	}
	
	public void setRows(Integer rows) {
		this.rows = rows;
	}
}
