
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class History extends DomainEntity {

	// Atributos ------------------
	private InceptionRecord 					inceptionRecord;
	private Collection<MiscellaneousRecord> 	miscellaneousRecord;
	private Collection<LegalRecord> 			legalRecord;
	private Collection<PeriodRecord> 			periodRecord;
	private Collection<LinkRecord> 				linkRecord;

	// Getters
	
	@OneToOne(cascade = CascadeType.ALL)
	public InceptionRecord getInceptionRecord() {
		return this.inceptionRecord;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<PeriodRecord> getPeriodRecord() {
		return this.periodRecord;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<LegalRecord> getLegalRecord() {
		return this.legalRecord;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMiscellaneousRecord() {
		return this.miscellaneousRecord;
	}
	
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<domain.LinkRecord> getLinkRecord() {
		return this.linkRecord;
	}

	// Setters

	public void setMiscellaneousRecord(Collection<MiscellaneousRecord> miscellaneousRecord) {
		this.miscellaneousRecord = miscellaneousRecord;
	}
	
	public void setLegalRecord(Collection<LegalRecord> legalRecord) {
		this.legalRecord = legalRecord;
	}

	public void setPeriodRecord(Collection<PeriodRecord> periodRecord) {
		this.periodRecord = periodRecord;
	}
	
	public void setInceptionRecord(InceptionRecord inceptionRecord) {
		this.inceptionRecord = inceptionRecord;
	}
	
	public void setLinkRecord(Collection<domain.LinkRecord> linkRecord) {
		this.linkRecord = linkRecord;
	}
	
	//Relaciones
	private Brotherhood	brotherhood;
	
	@OneToOne
	public Brotherhood getBrotherhood() {
		return brotherhood;
	}
	
	public void setBrotherhood(Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

}
