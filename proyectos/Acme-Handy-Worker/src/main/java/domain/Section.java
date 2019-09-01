
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Section extends DomainEntity {

	private String			title, text;
	private int				indice;
	private List<String>	pictureUrl;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@ElementCollection
	public List<String> getPictureUrl() {
		return this.pictureUrl;
	}

	public void setPictureUrl(final List<String> pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public int getIndice() {
		return this.indice;
	}

	public void setIndice(final int indice) {
		this.indice = indice;
	}

}
