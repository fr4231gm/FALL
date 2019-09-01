
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = @javax.persistence.UniqueConstraint(columnNames = {
	"ticker"
}))
public class Post extends DomainEntity {

	// Atributos ---------------------------------------------------------------
	private String	ticker;
	private Date	moment;
	private String	description;
	private String	title;
	private Double	score;
	private Boolean	isDraft;
	private String	pictures;
	private String	category;
	private String	stl;


	// Constructor ------------------------------------------------------------
	public Post() {
		super();
	}

	// Getters ----------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTicker() {
		return this.ticker;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public Boolean getIsDraft() {
		return this.isDraft;
	}

	// @NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPictures() {
		return this.pictures;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getCategory() {
		return this.category;
	}

	@URL
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getStl() {
		return this.stl;
	}

	@Range(min = 0, max = 5)
	public Double getScore() {
		return this.score;
	}

	// Setters ----------------------------------------------------------------
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setIsDraft(final Boolean isDraft) {
		this.isDraft = isDraft;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	public void setStl(final String stl) {
		this.stl = stl;
	}

	public void setScore(final Double score) {
		this.score = score;
	}


	// Relationships-----------------------------------------------------------
	private Designer	designer;
	private Guide		guide;


	// Getters-----------------------------------------------------------------

	@ManyToOne(optional = false)
	public Designer getDesigner() {
		return this.designer;
	}

	@OneToOne(optional = false)
	public Guide getGuide() {
		return this.guide;
	}

	// Setters-------------------------------------------------------------

	public void setDesigner(final Designer designer) {
		this.designer = designer;
	}

	public void setGuide(final Guide guide) {
		this.guide = guide;
	}

	// toString ---------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Post [ticker=" + ticker + ", moment=" + moment
				+ ", description=" + description + ", title=" + title
				+ ", score=" + score + ", isDraft=" + isDraft + ", pictures="
				+ pictures + ", category=" + category + ", stl=" + stl
				+ ", designer=" + designer.getName() + ", guide=" + guide + "]";
	}

}
