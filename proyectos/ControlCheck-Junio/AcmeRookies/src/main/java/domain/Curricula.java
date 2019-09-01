
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	// Atributos ------------------
	private PersonalData					personalData;
	private Collection<MiscellaneousData>	miscellaneousData;
	private Collection<EducationData>		educationData;
	private Collection<PositionData>		positionData;


	// Getters

	@OneToOne(cascade = CascadeType.ALL)
	public PersonalData getPersonalData() {
		return this.personalData;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationData> getEducationData() {
		return this.educationData;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousData> getMiscellaneousData() {
		return this.miscellaneousData;

	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<PositionData> getPositionData() {
		return this.positionData;
	}

	// Setters

	public void setPersonalData(final PersonalData personalData) {
		this.personalData = personalData;
	}

	public void setMiscellaneousData(final Collection<MiscellaneousData> miscellaneousData) {
		this.miscellaneousData = miscellaneousData;
	}

	public void setEducationData(final Collection<EducationData> educationData) {
		this.educationData = educationData;
	}

	public void setPositionData(final Collection<PositionData> positionData) {
		this.positionData = positionData;
	}


	//Relaciones
	private Rookie	rookie;


	@ManyToOne(optional = false)
	public Rookie getRookie() {
		return this.rookie;
	}

	public void setRookie(final Rookie rookie) {
		this.rookie = rookie;
	}

}
