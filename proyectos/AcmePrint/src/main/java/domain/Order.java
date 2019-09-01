
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "`order`")
public class Order extends DomainEntity {

	// Atributos ---------------------------------------------------------------

	private Date	moment;
	private String	stl;
	private String	material;
	private String	status;
	private String	comments;
	private Boolean	isDraft;
	private Boolean	isCancelled;


	// Constructor ------------------------------------------------------------
	public Order() {
		super();
	}

	// Getters ----------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getStl() {
		return this.stl;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getMaterial() {
		return this.material;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getStatus() {
		return this.status;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getComments() {
		return this.comments;
	}

	public Boolean getIsDraft() {
		return this.isDraft;
	}

	public Boolean getIsCancelled() {
		return this.isCancelled;
	}

	// Setters ----------------------------------------------------------------
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setStl(final String stl) {
		this.stl = stl;
	}

	public void setMaterial(final String material) {
		this.material = material;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setIsDraft(final Boolean isDraft) {
		this.isDraft = isDraft;
	}

	public void setIsCancelled(final Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}


	// Relaciones --------------------------------------------------------------
	private Customer	customer;
	private Post		post;


	// Getters ----------------------------------------------------------------

	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	@ManyToOne(optional = true)
	public Post getPost() {
		return this.post;
	}

	// Setters ----------------------------------------------------------------
	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public void setPost(final Post post) {
		this.post = post;
	}

	// toString ---------------------------------------------------------------

	@Override
	public String toString() {
		return "Order [moment=" + this.moment + ", stl=" + this.stl + ", material=" + this.material + ", status=" + this.status + ", comments=" + this.comments + ", isDraft=" + this.isDraft + ", isCancelled=" + this.isCancelled + ", customer="
			+ this.customer.getName() + ", post=" + this.post + "]";
	}

}
