
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = @javax.persistence.UniqueConstraint(columnNames = {
	"ticker"
}))
public class SparePart extends DomainEntity {

	// Atributos ---------------------------------------------------------------
	private String	ticker;
	private String	name;
	private String	description;
	private Date	purchaseDate;
	private Double	purchasePrice;
	private String	photo;


	// Constructor ------------------------------------------------------------
	public SparePart() {
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

	//@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPhoto() {
		return this.photo;
	}

	// Setters ----------------------------------------------------------------
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

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

	// toString ---------------------------------------------------------------
	@Override
	public String toString() {
		return "SparePart [ticker=" + ticker + ", name=" + name
				+ ", description=" + description + ", purchaseDate="
				+ purchaseDate + ", purchasePrice=" + purchasePrice
				+ ", photo=" + photo + "]";
	}
}
