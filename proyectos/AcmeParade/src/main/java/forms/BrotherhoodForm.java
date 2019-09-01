package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Area;

public class BrotherhoodForm extends ActorForm{

	private String title;
	private String pictures;
	private Date establishmentDate;
	private Area area;
	
	// Getters ----------------------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public final String getTitle() {
		return this.title;
	}
	public final String getPictures() {
		return this.pictures;
	}
	@Past
	@NotNull
	// debe ser anterior a hoy
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public final Date getEstablishmentDate() {
		return this.establishmentDate;
	}
	public final Area getArea(){
		return this.area;
	}
	
	// Setters ----------------------------------------------------------------
	
	public final void setTitle(String title) {
		this.title = title;
	}
	public final void setPictures(String pictures) {
		this.pictures = pictures;
	}
	public final void setEstablishmentDate(Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}
	public final void setArea(Area area){
		this.area = area;
	}
	
	
	
	
	
}
