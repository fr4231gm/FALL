package forms;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import domain.DomainEntity;
import domain.Tutorial;

public class SectionForm extends DomainEntity {

	// Atributos
	private int id;
	private int version;
	private String title;
	private String summary;
	private String pictures;
	private Tutorial tutorial;

	// Getters
	public int getId() {
		return this.id;
	}

	public int getVersion() {
		return this.version;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSummary() {
		return this.summary;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Column(length = 5000)
	public String getPictures() {
		return this.pictures;
	}

	@NotNull
	public Tutorial getTutorial() {
		return this.tutorial;
	}

	// Setters
	public void setId(int id) {
		this.id = id;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	public void setTutorial(Tutorial tutorial) {
		this.tutorial = tutorial;
	}

}
