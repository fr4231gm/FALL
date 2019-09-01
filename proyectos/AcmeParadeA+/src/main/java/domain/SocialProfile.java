
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialProfile extends DomainEntity {

	// Atributos
	private String	nick;
	private String	socialNetwork;
	private String	link;


	// Constructor
	public SocialProfile() {
		super();
	}

	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getNick() {
		return this.nick;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSocialNetwork() {
		return this.socialNetwork;
	}

	@URL
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getLink() {
		return this.link;
	}

	// Setters
	public void setNick(final String nick) {
		this.nick = nick;
	}

	public void setSocialNetwork(final String socialNetwork) {
		this.socialNetwork = socialNetwork;
	}

	public void setLink(final String link) {
		this.link = link;
	}


	// Relaciones
	private Actor	actor;


	// Getters
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	// Setters
	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
