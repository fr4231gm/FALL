package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import domain.DomainEntity;
import domain.Inventory;

public class SparePartForm extends DomainEntity {


	// Atributos ---------------------------------------------------------------
	private String	name;
	private String	description;
	private Date	purchaseDate;
	private Double	purchasePrice;
	private String	photo;
	private Inventory inventory;

	// Getters ----------------------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	@Range(min = 0, max = 9999)
	public Double getPurchasePrice() {
		return this.purchasePrice;
	}
	
	public Inventory getInventory(){
		return this.inventory;
	}
	
	//@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPhoto() {
		return this.photo;
	}

	// Setters ----------------------------------------------------------------
	public void setName(final String name) {
		this.name = name;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setPurchaseDate(final Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public void setPurchasePrice(final Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}
	
	public void setInventory(final Inventory inventory) {
		this.inventory = inventory;
	}


}