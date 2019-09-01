
package forms;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Company;
import domain.DomainEntity;
import domain.Problem;

public class PositionForm extends DomainEntity {

	// Atributo
	// -----------------------------------------------------------------

	private String	title;
	private String	description;
	private String	ticker;
	private Date	deadline;
	private String	profile;
	private String	skills;
	private String	technologies;
	private Double	salary;
	private Boolean	isDraft;
	private Boolean	isCancelled;


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
	@Column(unique = true)
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTicker() {
		return this.ticker;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeadline() {
		return this.deadline;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getProfile() {
		return this.profile;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSkills() {
		return this.skills;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTechnologies() {
		return this.technologies;
	}

	@NotNull
	@Range(min = 0, max = 99999)
	public Double getSalary() {
		return this.salary;
	}

	public Boolean getIsDraft() {
		return this.isDraft;
	}

	public Boolean getIsCancelled() {
		return this.isCancelled;
	}

	// Setters ----------------------------------------------------------------
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	public void setProfile(final String profile) {
		this.profile = profile;
	}

	public void setSkills(final String skills) {
		this.skills = skills;
	}

	public void setTechnologies(final String technologies) {
		this.technologies = technologies;
	}

	public void setSalary(final Double salary) {
		this.salary = salary;
	}

	public void setIsDraft(final Boolean isDraft) {
		this.isDraft = isDraft;
	}

	public void setIsCancelled(final Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}


	// Relations
	// --------------------------------------------------------------------------

	private Company				company;
	private Collection<Problem>	problems;


	// Getters
	// ----------------------------------------------------------------------------

	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}

	@OneToMany
	public Collection<Problem> getProblems() {
		return this.problems;
	}

	// Setters
	// ----------------------------------------------------------------------------
	public void setCompany(final Company company) {
		this.company = company;
	}

	public void setProblems(final Collection<Problem> problems) {
		this.problems = problems;
	}
}
