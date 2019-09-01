
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	private String				name;
	private Boolean				isSystem;
	private Box					parentBox;
	private Collection<Message>	messages;
	private Actor				actor;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Boolean getIsSystem() {
		return this.isSystem;
	}

	public void setIsSystem(final Boolean isSystem) {
		this.isSystem = isSystem;
	}

	@ManyToOne(optional = true)
	public Box getParentBox() {
		return this.parentBox;
	}

	public void setParentBox(final Box parentBox) {
		this.parentBox = parentBox;
	}

	@ManyToMany(mappedBy = "boxes")
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

	@ManyToOne(optional = false)
	@Valid
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
