
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Designer extends Actor {

	// Attributes -------------------------------------------------------------
	private Double	score;


	// Constructor ------------------------------------------------------------
	public Designer() {
		super();
	}

	@Range(min = 0, max = 5)
	public Double getScore() {
		return this.score;
	}

	public void setScore(final Double score) {
		this.score = score;
	}

	

	// Getters ----------------------------------------------------------------

	// Setters ----------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	// Attributes --------------------------------------------------------------

	// Getters ----------------------------------------------------------------

	// Setters ----------------------------------------------------------------

	// toString ---------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Designer ["+ super.toString() + "score=" + score + "]";
	}
	
	
}
