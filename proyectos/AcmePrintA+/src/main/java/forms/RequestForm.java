package forms;

import java.util.Date;

import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import domain.DomainEntity;
import domain.Order;
import domain.PrintSpooler;

public class RequestForm extends DomainEntity {

	// Atributos ---------------------------------------------------------------
	private PrintSpooler printSpooler;
	private Integer extruderTemp;
	private Integer hotbedTemp;
	private Double layerHeight;
	private Date startDate;
	private Date endDate;

	// Getters ----------------------------------------------------------------

	@NotNull
	public PrintSpooler getPrintSpooler () {
		return this.printSpooler;
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

	public void setPrintSpooler(PrintSpooler printSpooler) {
		this.printSpooler = printSpooler;
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
