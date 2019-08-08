
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Access(AccessType.PROPERTY)
@Embeddable
public class Paper {

	// Atributos
	private String	title;
	private String	summary;
	private String	document;
	private boolean	cameraReadyPaper;


	// Getters
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

	@NotBlank
	@URL
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDocument() {
		return this.document;
	}

	// Setters
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public void setDocument(final String document) {
		this.document = document;
	}

	public boolean isCameraReadyPaper() {
		return this.cameraReadyPaper;
	}

	public void setCameraReadyPaper(final boolean cameraReadyPaper) {
		this.cameraReadyPaper = cameraReadyPaper;
	}

}
