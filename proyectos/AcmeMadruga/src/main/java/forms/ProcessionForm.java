package forms;

import java.util.Collection;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import domain.DomainEntity;

public class ProcessionForm extends DomainEntity {
	
	private Date moment;
	private String	title;
	private String	description;
	private Boolean	isDraft;
	private int 	id;
	private int		version;
	private String ticker;
	private Integer	rows;

	//Getters -------------------------------------------
	
	public Date getMoment(){
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
	
	public int getId() {
		return this.id;
	}

	public int getVersion() {
		return this.version;
	}
	public String getTicker() {
		return ticker;
	}
	
	@Range(min=1, max=5)
	public Integer getRows() {
		return rows;
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
	
	public void setId(final int id) {
		this.id = id;
	}

	public void setVersion(final int version) {
		this.version = version;
	}
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
	// Relations
	
	private Collection<domain.Float> floats;
	
	
	public Collection<domain.Float> getFloats(){
		return this.floats;
	}
	
	public void setFloats(final Collection<domain.Float> floatts){
		this.floats = floatts;
	}
	
}
