package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = @javax.persistence.UniqueConstraint(columnNames = { "ticker" }))
public class Spool extends DomainEntity {

	// Atributos --------------------------------------------------------------
	private String ticker;
	private String materialName;
	private Double diameter;
	private Double length;
	private Double remainingLength;
	private Double weight;
	private String color;
	private Integer meltingTemperature;
	
	// Constructor ------------------------------------------------------------
	public Spool() {
		super();
	}

	// Getters ----------------------------------------------------------------
	@NotBlank
	@Column(unique = true)
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTicker() {
		return this.ticker;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getMaterialName() {
		return this.materialName;
	}

	@NotNull
	@Range(min = 0, max = 5)
	public Double getDiameter() {
		return this.diameter;
	}

	@NotNull
	@Range(min = 0, max = 400)
	public Double getLength() {
		return this.length;
	}

	@NotNull
	@Range(min = 0, max = 400)
	public Double getRemainingLength() {
		return this.remainingLength;
	}

	@NotNull
	@Range(min = 0, max = 4)
	public Double getWeight() {
		return this.weight;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getColor() {
		return this.color;
	}

	@NotNull
	@Range(min = 0, max = 300)
	public Integer getMeltingTemperature() {
		return this.meltingTemperature;
	}
	

	// Setters ----------------------------------------------------------------
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public void setDiameter(Double diameter) {
		this.diameter = diameter;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public void setRemainingLength(Double remainingLength) {
		this.remainingLength = remainingLength;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setMeltingTemperature(Integer meltingTemperature) {
		this.meltingTemperature = meltingTemperature;
	}

	// toString ---------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Spool [ticker=" + ticker + ", materialName=" + materialName
				+ ", diameter=" + diameter + ", length=" + length
				+ ", remainingLength=" + remainingLength + ", weight=" + weight
				+ ", color=" + color + ", meltingTemperature="
				+ meltingTemperature + "]";
	}
}
