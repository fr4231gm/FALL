package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import domain.DomainEntity;
import domain.Inventory;

public class SpoolForm extends DomainEntity {


	// Atributos ---------------------------------------------------------------
	private String materialName;
	private Double diameter;
	private Double length;
	private Double remainingLength;
	private Double weight;
	private String color;
	private Integer meltingTemperature;
	private Inventory inventory;


	// Getters ----------------------------------------------------------------
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
	
	public Inventory getInventory(){
		return this.inventory;
	}

	// Setters ----------------------------------------------------------------
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
	
	public void setInventory(final Inventory inventory) {
		this.inventory = inventory;
	}


}