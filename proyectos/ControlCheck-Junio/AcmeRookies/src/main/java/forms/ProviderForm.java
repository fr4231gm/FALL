package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class ProviderForm extends ActorForm{
	
	//Atributos
	private String makeProvider;
	
	//Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getMakeProvider(){
		return makeProvider;
	}
	
	//Setters
	public void setMakeProvider(final String make){
		this.makeProvider = make;
	}
}

