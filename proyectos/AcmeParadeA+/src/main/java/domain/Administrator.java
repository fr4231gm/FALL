
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
// para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class Administrator extends Actor {

	// Atributos -- no tiene

	// Constructor -- vacio por defecto
	public Administrator() {
		super();
	}

	// Getters -- no tiene

	// Setters -- no tiene

	// Relaciones -- no tiene
}
