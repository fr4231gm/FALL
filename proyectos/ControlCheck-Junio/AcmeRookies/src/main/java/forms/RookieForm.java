package forms;

import javax.persistence.OneToOne;

import domain.Finder;

public class RookieForm extends ActorForm {

	// Relaciones -------------------------------
	private Finder finder;

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
