package forms;

import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import domain.DomainEntity;

public class SponsorshipForm extends DomainEntity{
	private int id;
	private String	banner;
	private String	targetURL;
	private Boolean isEnabled;
	private Integer creditCardId;
	private Integer paradeId;

	//Getters --------------------------------------------
	
	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}
	
	@NotBlank
	@URL
	public String getTargetURL() {
		return this.targetURL;
	}

	@NotNull
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	
	@Valid
	@ManyToOne(optional = false)
	public Integer getCreditCardId() {
		return creditCardId;
	}
	
	@Valid
	@ManyToOne
	public Integer getParadeId() {
		return paradeId;
	}
	
	public int getId(){
		return this.id;
	}
	
	//Setters--------------------------------------------------
	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setTargetURL(final String targetURL) {
		this.targetURL = targetURL;
	}
	
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public void setCreditCardId(Integer creditCardId) {
		this.creditCardId = creditCardId;
	}

	public void setParadeId(Integer paradeId) {
		this.paradeId = paradeId;
	}
	public void setId(final int id){
		this.id = id;
	}
}
