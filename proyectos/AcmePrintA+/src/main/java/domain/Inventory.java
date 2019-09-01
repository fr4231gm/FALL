
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Inventory extends DomainEntity {

	// Atributos --------------------------------------------------------------
	private String					ticker;
	private Collection<Printer>		printers;
	private Collection<Spool>		spools;
	private Collection<SparePart>	spareParts;


	// Getters ----------------------------------------------------------------

	public String getTicker() {
		return this.ticker;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Printer> getPrinters() {
		return this.printers;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Spool> getSpools() {
		return this.spools;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<SparePart> getSpareParts() {
		return this.spareParts;
	}

	// Setters ----------------------------------------------------------------
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public void setPrinters(final Collection<Printer> printers) {
		this.printers = printers;
	}

	public void setSpools(final Collection<Spool> spools) {
		this.spools = spools;
	}

	public void setSpareParts(final Collection<SparePart> spareParts) {
		this.spareParts = spareParts;
	}


	//Relaciones
	private Company	company;


	// Getters ----------------------------------------------------------------
	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}

	// Setters ----------------------------------------------------------------
	public void setCompany(final Company company) {
		this.company = company;
	}

	// toString ---------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Inventory [ticker=" + ticker + ", printers=" + printers
				+ ", spools=" + spools + ", spareParts=" + spareParts
				+ ", company=" + company.getCommercialName() + "]";
	}
	
}
