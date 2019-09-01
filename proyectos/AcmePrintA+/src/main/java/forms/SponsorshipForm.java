package forms;

import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import domain.DomainEntity;

public class SponsorshipForm extends DomainEntity {

	// Atributos --------------------------------------------------------------
	private String banner;
	private String targetPage;
	private Boolean isEnabled;
	private Double	cost;
	private Integer postId;
	private Integer providerId;
	// Getters ----------------------------------------------------------------

	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	@NotBlank
	@URL
	public String getTargetPage() {
		return this.targetPage;
	}

	@NotNull
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	
	@Range(min = 0, max = 99999)
	public Double getCost(){
		return this.cost;
	}

	@Valid
	@ManyToOne
	public Integer getPostId() {
		return this.postId;
	}
	
	@Valid
	@ManyToOne
	public Integer getProviderId() {
		return this.providerId;
	}

	// Setters ----------------------------------------------------------------
	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setTargetPage(final String targetPage) {
		this.targetPage = targetPage;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setCost(Double cost){
		this.cost = cost;
	}
	
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}


}
