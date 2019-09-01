
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Segment extends DomainEntity {

	// Atributos

	private Date		startTime;
	private Date		endTime;
	private Coordinate	origin;
	private Coordinate	destination;


	// Getters
	@AttributeOverrides({
		@AttributeOverride(name = "latitude", column = @Column(name = "originLatitude")), @AttributeOverride(name = "longitude", column = @Column(name = "originLongitude"))
	})
	@Valid
	public Coordinate getOrigin() {
		return this.origin;
	}

	@AttributeOverrides({
		@AttributeOverride(name = "latitude", column = @Column(name = "destinationLatitude")), @AttributeOverride(name = "longitude", column = @Column(name = "destinationLongitude"))
	})
	@Valid
	public Coordinate getDestination() {
		return this.destination;
	}

	@Temporal(TemporalType.TIME)
	//the date os given by the parade so we only take in count the time
	public Date getStartTime() {
		return this.startTime;
	}

	@Temporal(TemporalType.TIME)
	//the date os given by the parade so we only take in count the time
	public Date getEndTime() {
		return this.endTime;
	}

	// Setters

	public void setOrigin(final Coordinate origin) {
		this.origin = origin;
	}

	public void setDestination(final Coordinate destination) {
		this.destination = destination;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(final Date endTime) {
		this.endTime = endTime;
	}

}
