
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Embeddable
// para indicar que se trata de un datatype
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class Coordinate {

	//Atributos
	private Double	longitude;
	private Double	latitude;


	//Constructor
	public Coordinate() {
		super();
	}

	//Getters
	@NotNull
	@Range(min = -180, max = 180)
	public Double getLongitude() {
		return this.longitude;
	}

	@NotNull
	@Range(min = -90, max = 90)
	public Double getLatitude() {
		return this.latitude;
	}

	//Setters

	public void setLongitude(final Double longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(final Double latitude) {
		this.latitude = latitude;
	}
}
