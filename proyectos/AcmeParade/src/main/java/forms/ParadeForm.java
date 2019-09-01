
package forms;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import domain.DomainEntity;

public class ParadeForm extends DomainEntity {

	private Date	moment;
	private String	title;
	private String	description;
	private Boolean	isDraft;
	private int		id;
	private int		version;
	private String	ticker;
	private Integer	rows;
	private String	status;
	private String	rejectReason;


	//Getters -------------------------------------------

	public Date getMoment() {
		return this.moment;
	}

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

	public Boolean getisDraft() {
		return this.isDraft;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public int getVersion() {
		return this.version;
	}
	public String getTicker() {
		return this.ticker;
	}

	@NotNull
	@Range(min = 1, max = 5)
	public Integer getRows() {
		return this.rows;
	}
	public String getStatus() {
		return this.status;
	}

	public String getRejectReason() {
		return this.rejectReason;
	}

	//Setters -------------------------------------------

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setIsDraft(final Boolean isDraft) {
		this.isDraft = isDraft;
	}

	@Override
	public void setId(final int id) {
		this.id = id;
	}

	@Override
	public void setVersion(final int version) {
		this.version = version;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public void setRows(final Integer rows) {
		this.rows = rows;
	}


	// Relations

	private Collection<domain.Float>	floats;


	public Collection<domain.Float> getFloats() {
		return this.floats;
	}

	public void setFloats(final Collection<domain.Float> floatts) {
		this.floats = floatts;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public void setRejectReason(final String rejectReason) {
		this.rejectReason = rejectReason;
	}

}
