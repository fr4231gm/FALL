package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	// Atributos ---------------------------------------------------------------
	private int number;
	private Integer extruderTemp;
	private Integer hotbedTemp;
	private Double layerHeight;
	private Date startDate;
	private Date endDate;
	
	

	// Constructor ------------------------------------------------------------
	public Request() {
		super();
	}

	// Getters ----------------------------------------------------------------

	@NotNull
	@Min(value = 0)
	public int getNumber() {
		return this.number;
	}

	@NotNull
	@Range(min = 0, max = 400)
	public Integer getExtruderTemp() {
		return this.extruderTemp;
	}

	@NotNull
	@Range(min = 0, max = 200)
	public Integer getHotbedTemp() {
		return this.hotbedTemp;
	}

	@NotNull
	@Range(min = 0, max = 2)
	public Double getLayerHeight() {
		return this.layerHeight;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}
	
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}


	// Setters ----------------------------------------------------------------

	public void setNumber(int number) {
		this.number = number;
	}
	public void setExtruderTemp(Integer extruderTemp) {
		this.extruderTemp = extruderTemp;
	}
	public void setHotbedTemp(Integer hotbedTemp) {
		this.hotbedTemp = hotbedTemp;
	}
	public void setLayerHeight(Double layerHeight) {
		this.layerHeight = layerHeight;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	// Relaciones ----------------------------------------------------------------
	
	private Order order;
	
	@OneToOne(optional = true)
	public Order getOrder() {
		return this.order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
