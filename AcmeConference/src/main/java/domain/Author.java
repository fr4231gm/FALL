package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Author extends Actor {

	// Atributos
	private Double score;

	// Getters
	@Range(min = 0, max = 1)
	public Double getScore() {
		return this.score;
	}

	// Setters
	public void setScore(final Double score) {
		this.score = score;
	}
}
