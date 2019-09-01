
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
// para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class Member extends Actor {

	//Atributos -- no tiene

	//Constructor
	public Member() {
		super();
	}


	//Relaciones
	private Finder	finder;


	//Getters
	@OneToOne(optional = false)
	public Finder getFinder() {
		return this.finder;
	}

	//Setters
	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

}
