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
	private String					camerareadypaper;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getCamerareadypaper(){
		return this.camerareadypaper;
	}



	// Setters
	public void setCamerareadypaper(final String camerareadypaper){
		this.camerareadypaper = camerareadypaper; 
	}

}