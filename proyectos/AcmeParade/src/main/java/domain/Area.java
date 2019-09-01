
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
// para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class Area extends DomainEntity {

	// Atributos

	private String	name;
	private String	pictures;


	// Constructor -- vacio por defecto
	public Area() {
		super();
	}

	// Getters
	@NotBlank
	public String getName() {
		return this.name;
	}

	@URL
	public String getPictures() {
		return this.pictures;
	}

	// Setters
	public void setName(final String name) {
		this.name = name;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}


	// Relaciones
	private Chapter	chapter;


	// Getters

	@OneToOne
	public Chapter getChapter() {
		return this.chapter;
	}

	// Setters
	public void setChapter(final Chapter chapter) {
		this.chapter = chapter;
	}
}
