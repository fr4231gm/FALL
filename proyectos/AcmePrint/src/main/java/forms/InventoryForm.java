
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import domain.DomainEntity;

public class InventoryForm extends DomainEntity {

	// Atributos ---------------------------------------------------------------
	private String	make;
	private String	model;
	private String	description;
	private Date	datePurchase;
	private Date	warrantyDate;
	private String	shoppingWebsite;
	private Integer	dimensionX;
	private Integer	dimensionY;
	private Integer	dimensionZ;
	private Double	extruderDiameter;
	private Double	nozzle;
	private String	materials;
	private String	photo;
	private Boolean	isActive;

	private String	materialName;
	private Double	diameter;
	private Double	length;
	private Double	remainingLength;
	private Double	weight;
	private String	color;
	private Integer	meltingTemperature;


	// Getters ----------------------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getMake() {
		return this.make;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getModel() {
		return this.model;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	public Date getDatePurchase() {
		return this.datePurchase;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getWarrantyDate() {
		return this.warrantyDate;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getShoppingWebsite() {
		return this.shoppingWebsite;
	}

	@NotNull
	@Range(min = 0, max = 1000)
	public Integer getDimensionX() {
		return this.dimensionX;
	}

	@NotNull
	@Range(min = 0, max = 1000)
	public Integer getDimensionY() {
		return this.dimensionY;
	}

	@NotNull
	@Range(min = 0, max = 1000)
	public Integer getDimensionZ() {
		return this.dimensionZ;
	}

	@NotNull
	@Range(min = 0, max = 5)
	public Double getExtruderDiameter() {
		return this.extruderDiameter;
	}

	@NotNull
	@Range(min = 0, max = 2)
	public Double getNozzle() {
		return this.nozzle;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getMaterials() {
		return this.materials;
	}

	//@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPhoto() {
		return this.photo;
	}

	public Boolean getIsActive() {
		return this.isActive;
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
	public void setMake(final String make) {
		this.make = make;
	}
	public void setModel(final String model) {
		this.model = model;
	}
	public void setDescription(final String description) {
		this.description = description;
	}
	public void setDatePurchase(final Date datePurchase) {
		this.datePurchase = datePurchase;
	}
	public void setWarrantyDate(final Date warrantyDate) {
		this.warrantyDate = warrantyDate;
	}
	public void setShoppingWebsite(final String shoppingWebsite) {
		this.shoppingWebsite = shoppingWebsite;
	}
	public void setDimensionX(final Integer dimensionX) {
		this.dimensionX = dimensionX;
	}
	public void setDimensionY(final Integer dimensionY) {
		this.dimensionY = dimensionY;
	}
	public void setDimensionZ(final Integer dimensionZ) {
		this.dimensionZ = dimensionZ;
	}
	public void setExtruderDiameter(final Double extruderDiameter) {
		this.extruderDiameter = extruderDiameter;
	}
	public void setNozzle(final Double nozzle) {
		this.nozzle = nozzle;
	}
	public void setMaterials(final String materials) {
		this.materials = materials;
	}
	public void setPhoto(final String photo) {
		this.photo = photo;
	}
	public void setIsActive(final Boolean isActive) {
		this.isActive = isActive;
	}

	public void setMaterialName(final String materialName) {
		this.materialName = materialName;
	}

	public void setDiameter(final Double diameter) {
		this.diameter = diameter;
	}

	public void setLength(final Double length) {
		this.length = length;
	}

	public void setRemainingLength(final Double remainingLength) {
		this.remainingLength = remainingLength;
	}

	public void setWeight(final Double weight) {
		this.weight = weight;
	}

	public void setColor(final String color) {
		this.color = color;
	}

	public void setMeltingTemperature(final Integer meltingTemperature) {
		this.meltingTemperature = meltingTemperature;
	}

}
