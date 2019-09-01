
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
		"printer"
	})
})
public class PrintSpooler extends DomainEntity {

	// Atributos --------------------------------------------------------------

	// Constructor ------------------------------------------------------------
	public PrintSpooler() {
		super();
	}


	// Relaciones -------------------------------------------------------------
	private Printer				printer;
	private Collection<Request>	requests;


	// Getters ----------------------------------------------------------------

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Request> getRequests() {
		return this.requests;
	}

	@OneToOne(optional = false)
	public Printer getPrinter() {
		return this.printer;
	}

	// Setters ----------------------------------------------------------------
	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}

	public void setPrinter(final Printer printer) {
		this.printer = printer;
	}
	
	// toString ---------------------------------------------------------------

	@Override
	public String toString() {
		return "PrintSpooler [printer=" + printer + ", requests=" + requests
				+ "]";
	}

}
