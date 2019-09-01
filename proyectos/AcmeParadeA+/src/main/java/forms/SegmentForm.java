
package forms;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import domain.Coordinate;

public class SegmentForm {

	// Atributos

	private Integer		startTimeHour;
	private Integer		startTimeMinutes;
	private Integer		endTimeHour;
	private Integer		endTimeMinutes;
	private Coordinate	origin;
	private Coordinate	destination;
	private Integer		version;
	private Integer		id;


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
	@NotNull
	@Range(min = 0, max = 23)
	public Integer getStartTimeHour() {

		return this.startTimeHour;
	}
	@NotNull
	@Range(min = 0, max = 59)
	public Integer getStartTimeMinutes() {
		return this.startTimeMinutes;
	}

	// Setters

	public void setOrigin(final Coordinate origin) {
		this.origin = origin;
	}

	public void setDestination(final Coordinate destination) {
		this.destination = destination;
	}

	public void setStartTimeHour(final Integer startTimeHour) {
		this.startTimeHour = startTimeHour;
	}
	@NotNull
	@Range(min = 0, max = 23)
	public Integer getEndTimeHour() {
		return this.endTimeHour;
	}

	public void setEndTimeHour(final Integer endTimeHour) {
		this.endTimeHour = endTimeHour;
	}

	public void setStartTimeMinutes(final Integer startTimeMinutes) {
		this.startTimeMinutes = startTimeMinutes;
	}

	@NotNull
	@Range(min = 0, max = 59)
	public Integer getEndTimeMinutes() {
		return this.endTimeMinutes;
	}

	public void setEndTimeMinutes(final Integer endTimeMinutes) {
		this.endTimeMinutes = endTimeMinutes;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

}
