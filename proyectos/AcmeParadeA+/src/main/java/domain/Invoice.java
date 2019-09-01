
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@javax.persistence.UniqueConstraint(columnNames = {
		"sponsorship"
	})
})
public class Invoice extends DomainEntity {

	//Atributos----------------------------------------------------------------
	private Collection<Charge>	charges;


	//Constructor--------------------------------------------------------------
	public Invoice() {
		super();
	}

	//Getters------------------------------------------------------------------

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Charge> getCharges() {
		return this.charges;
	}

	//Setters------------------------------------------------------------------
	public void setCharges(final Collection<Charge> charges) {
		this.charges = charges;
	}


	//Relaciones---------------------------------------------------------------
	private Sponsorship	sponsorship;


	@OneToOne(optional = false)
	public Sponsorship getSponsorship() {
		return this.sponsorship;
	}

	public void setSponsorship(final Sponsorship sponsorship) {
		this.sponsorship = sponsorship;
	}

}
