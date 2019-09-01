package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import domain.DomainEntity;



public class FloatForm extends DomainEntity {
	private int id;
	private String	title;
	private String	description;
	private String	pictures;
	
	
	//Getters ----------------------------------------------------------------
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

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPictures() {
		return this.pictures;
	}
	
	public int getId(){
		return this.id;
	}
	
	
	//Setters ----------------------------------------------------------------
	
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}
	
	public void setId(final int id){
		this.id = id;
	}
}
