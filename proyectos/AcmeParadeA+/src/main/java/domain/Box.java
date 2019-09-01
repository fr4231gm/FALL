
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	// Atributos ------------------
	private String	name;
	private Boolean	isSystem;


	// Constructor
	public Box() {
		super();
	}

	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public Boolean getIsSystem() {
		return this.isSystem;
	}

	// Setters
	public void setName(final String name) {
		this.name = name;
	}

	public void setIsSystem(final Boolean isSystem) {
		this.isSystem = isSystem;
	}


	// Relaciones
	private Box					parentBox;
	private Collection<Message>	messages;
	private Actor				actor;


	// Getters
	@ManyToOne(optional = true)
	public Box getParentBox() {
		return this.parentBox;
	}

	@ManyToMany(mappedBy = "boxes")
	public Collection<Message> getMessages() {
		return this.messages;
	}

	@ManyToOne(optional = false)
	@Valid
	public Actor getActor() {
		return this.actor;
	}

	// Setters
	public void setParentBox(final Box parentBox) {
		this.parentBox = parentBox;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
