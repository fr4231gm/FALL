
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Indexed
public class Reviewer extends Actor {

	// Atributos
	private String					keywords;
	private Collection<Submission>	submissions;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	public String getKeywords() {
		return this.keywords;
	}

	@ManyToMany
	public Collection<Submission> getSubmissions() {
		return this.submissions;
	}

	// Setters
	public void setKeywords(final String keywords) {
		this.keywords = keywords;
	}

	public void setSubmissions(final Collection<Submission> submissions) {
		this.submissions = submissions;
	}

}
