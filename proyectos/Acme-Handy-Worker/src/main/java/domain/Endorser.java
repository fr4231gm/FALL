
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Endorser extends Actor {

	private Collection<Endorsement>	receivedEndorsements;


	@OneToMany(mappedBy = "endorsed")
	public Collection<Endorsement> getReceivedEndorsements() {
		return this.receivedEndorsements;
	}
	public void setReceivedEndorsements(final Collection<Endorsement> receivedEndorsements) {
		this.receivedEndorsements = receivedEndorsements;
	}
}
