package forms;

import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import domain.DomainEntity;

public class SponsorshipForm extends DomainEntity{
	private String	banner;
	private String	targetPage;
	private Boolean	isEnabled;
	private Integer	positionId;
	
	//Getters --------------------------------------------
	
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
		
		@Valid
		@ManyToOne
		public Integer getPositionId() {
			return positionId;
		}
		
		
		//Setters--------------------------------------------------
		public void setBanner(final String banner) {
			this.banner = banner;
		}

		public void setTargetPage(final String targetPage) {
			this.targetPage = targetPage;
		}
		
		public void setIsEnabled(Boolean isEnabled) {
			this.isEnabled = isEnabled;
		}
		
		public void setPositionId(Integer positionId){
			this.positionId = positionId;
		}

}
