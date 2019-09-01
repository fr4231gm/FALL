
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	// Atributos ---------------------------------------------------------------
	private String	title;
	private String	description;
	private String	type;
	private String	pictures;
	private Double	score;


	// Constructor ------------------------------------------------------------
	public Comment() {
		super();
	}

	// Getters ----------------------------------------------------------------

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getType() {
		return this.type;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPictures() {
		return this.pictures;
	}

	//@NotNull
	@Range(min = 0, max = 5)
	public Double getScore() {
		return this.score;
	}
	// Setters ----------------------------------------------------------------
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	public void setScore(final Double score) {
		this.score = score;
	}


	// Relaciones---------------------------------------------------------------
	private Post	post;


	// Getters------------------------------------------------------------------
	@ManyToOne(optional = false)
	public Post getPost() {
		return this.post;
	}

	// Setters------------------------------------------------------------------
	public void setPost(final Post post) {
		this.post = post;
	}

	// toString ---------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Comment [title=" + title + ", description=" + description
				+ ", type=" + type + ", pictures=" + pictures + ", score="
				+ score + ", post=" + post + "]";
	}

}
