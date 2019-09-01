
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Item extends DomainEntity {

	//Atributos ---------------------------------------------------------------
	private String	name;
	private String	description;
	private String	link;
	private String	pictures;


	//Constructor -------------------------------------------------------------
	public Item() {
		super();
	}

	//Getters -----------------------------------------------------------------

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

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@URL
	@NotBlank
	public String getLink() {
		return this.link;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPictures() {
		return this.pictures;
	}

	//Setters -----------------------------------------------------------------

	public void setName(final String name) {
		this.name = name;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	public void setLink(final String link) {
		this.link = link;
	}


	//Relations ---------------------------------------------------------------

	private Provider	provider;


	//Getters -----------------------------------------------------------------

	@ManyToOne(optional = false)
	public Provider getProvider() {
		return this.provider;
	}

	//Setters -----------------------------------------------------------------
	public void setProvider(final Provider provider) {
		this.provider = provider;
	}

}
