
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.URL;

@Entity
// para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class Area extends DomainEntity {

	// Atributos

	private String	name;
	private String	pictures;


	// Constructor -- vac�o por defecto
	public Area() {
		super();
	}

	// Getters
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

}
