
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
// para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class Proclaim extends DomainEntity {

	//Atributos
	private String	text;
	private Date	moment;


	// Constructor2 -- vacío por defecto
	public Proclaim() {
		super();
	}

	// Getters
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Size(min = 1, max = 250)
	public String getText() {
		return this.text;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return this.moment;
	}

	// Setters
	public void setText(final String text) {
		this.text = text;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	// Relaciones
	private Chapter	chapter;


	// Getters

	@ManyToOne(optional = false)
	public Chapter getChapter() {
		return this.chapter;
	}

	// Setters
	public void setChapter(final Chapter chapter) {
		this.chapter = chapter;
	}
}
