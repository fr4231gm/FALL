
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
// --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// --import javax.persistence.AccessType;
public class Rookie extends Actor {

	// Relaciones -------------------------------
	private Finder	finder;


	// Getters

	@OneToOne
	public Finder getFinder() {

		return this.finder;
	}

	// Setters

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

}
