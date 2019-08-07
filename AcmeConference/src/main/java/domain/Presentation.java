package domain;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Entity;

@Entity 
@Access(AccessType.PROPERTY) 
public class Presentation extends Activity {

	// Atributos
	private String					cameraReadyPaper;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getCameraReadyPaper(){
		return this.cameraReadyPaper;
	}

	// Setters
	public void setCameraReadyPaper(final String cameraReadyPaper){
		this.cameraReadyPaper = cameraReadyPaper; 
	}

}