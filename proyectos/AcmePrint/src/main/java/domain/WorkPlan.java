package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = @javax.persistence.UniqueConstraint(columnNames = { "ticker" }))
public class WorkPlan extends DomainEntity {

	// Atributos ---------------------------------------------------------------
	private String ticker;

	// Constructor ------------------------------------------------------------
	public WorkPlan() {
		super();
	}

	// Getters ----------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTicker() {
		return this.ticker;
	}

	// Setters ----------------------------------------------------------------
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	// Relaciones---------------------------------------------------------------
	private Collection<Phase> phases;
	private Application application;

	// Getters------------------------------------------------------------------

	@ManyToOne(optional = false)
	public Application getApplication() {
		return this.application;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Phase> getPhases() {
		return this.phases;
	}

	// Setters------------------------------------------------------------------

	public void setApplication(Application application) {
		this.application = application;
	}

	public void setPhases(Collection<Phase> phases) {
		this.phases = phases;
	}
}
