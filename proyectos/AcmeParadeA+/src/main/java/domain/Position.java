
package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
// para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de Oacceder a la entidad--import javax.persistence.AccessType;
public class Position extends DomainEntity {

	// Atributos
	private Map<String, String>	name;

	// Constructor -- vacio por defecto
	public Position() {
		super();
	}

	// Getters
	@ElementCollection
	public Map<String, String> getName() {
		return this.name;
	}

	// Setters
	public void setName(final Map<String, String> name) {
		this.name = name;
	}
}
