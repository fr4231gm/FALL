
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Past;

@Entity
// para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad --import javax.persistence.AccessType;
// Multi index BinaryTree of dropOutMoment and Position
@Table(indexes = {@Index (columnList = "dropOutMoment, position")}) //--import javax.persistence.AccessType;
public class Enrolment extends DomainEntity {

	// Atributos
	private Date	moment;
	private Date	dropOutMoment;


	// Constructor
	public Enrolment() {
		super();
	}

	// Getters
	@Past
	public Date getMoment() {
		return this.moment;
	}
	@Past
	public Date getDropOutMoment() {
		return this.dropOutMoment;
	}

	// Setters
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setDropOutMoment(final Date dropOutMoment) {
		this.dropOutMoment = dropOutMoment;
	}


	// Relaciones
	private Member		member;
	private Brotherhood	brotherhood;
	private Position	position;


	// Getters
	@ManyToOne(optional = false)
	public Member getMember() {
		return this.member;
	}

	@ManyToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	@ManyToOne(optional = true)
	public Position getPosition() {
		return this.position;
	}

	// Setters
	public void setMember(final Member member) {
		this.member = member;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}
}
