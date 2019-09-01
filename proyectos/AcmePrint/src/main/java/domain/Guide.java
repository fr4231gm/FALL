package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Guide extends DomainEntity {

	// Atributos ---------------------------------------------------------------
	private Integer extruderTemp;
	private Integer hotbedTemp;
	private Double layerHeight;
	private Integer printSpeed;
	private Integer retractionSpeed;
	private String advices;

	// Constructor ------------------------------------------------------------
	public Guide() {
		super();
	}

	// Getters ----------------------------------------------------------------

	@NotNull
	@Range(min = 0, max = 400)
	public Integer getExtruderTemp() {
		return extruderTemp;
	}

	@NotNull
	@Range(min = 0, max = 200)
	public Integer getHotbedTemp() {
		return hotbedTemp;
	}

	@NotNull
	@Range(min = 0, max = 2)
	public Double getLayerHeight() {
		return layerHeight;
	}

	@NotNull
	@Range(min = 0, max = 10000)
	public Integer getPrintSpeed() {
		return printSpeed;
	}

	@NotNull
	@Range(min = 0, max = 10000)
	public Integer getRetractionSpeed() {
		return retractionSpeed;
	}

	// @NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getAdvices() {
		return advices;
	}

	// Setters ----------------------------------------------------------------

	public void setExtruderTemp(Integer extruderTemp) {
		this.extruderTemp = extruderTemp;
	}

	public void setHotbedTemp(Integer hotbedTemp) {
		this.hotbedTemp = hotbedTemp;
	}

	public void setLayerHeight(Double layerHeight) {
		this.layerHeight = layerHeight;
	}

	public void setPrintSpeed(Integer printSpeed) {
		this.printSpeed = printSpeed;
	}

	public void setRetractionSpeed(Integer retractionSpeed) {
		this.retractionSpeed = retractionSpeed;
	}

	public void setAdvices(String advices) {
		this.advices = advices;
	}

	

	// Relaciones -------------------------------------------------------------
	

	// Getters ----------------------------------------------------------------
	

	// Setters ----------------------------------------------------------------
	
	// toString ---------------------------------------------------------------
	@Override
	public String toString() {
		return "Guide [extruderTemp=" + extruderTemp + ", hotbedTemp="
				+ hotbedTemp + ", layerHeight=" + layerHeight + ", printSpeed="
				+ printSpeed + ", retractionSpeed=" + retractionSpeed
				+ ", advices=" + advices + "]";
	}
}
