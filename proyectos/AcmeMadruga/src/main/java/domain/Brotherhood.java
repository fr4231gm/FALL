
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
// para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class Brotherhood extends Actor {

	//Atributos
	private String	title;
	private Date	establishmentDate;
	private String	pictures;


	// Constructor -- vac�o por defecto
	public Brotherhood() {
		super();
	}

	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	@Past
	// debe ser anterior a hoy
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEstablishmentDate() {
		return this.establishmentDate;
	}

	public String getPictures() {
		return this.pictures;
	}

	// Setters
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setEstablishmentDate(final Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}


	// Relaciones
	private Area	area;


	// Getters
	@ManyToOne(optional = false)
	public Area getArea() {
		return this.area;
	}

	// Setters
	public void setArea(final Area area) {
		this.area = area;
	}
}
