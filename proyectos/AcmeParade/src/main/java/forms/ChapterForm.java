package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class ChapterForm extends ActorForm{
	
	private String title;
	
	//Getters ----------------------------------
	
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public final String getTitle(){
		return this.title;
	}
	
	//Setters ----------------------------------
	
	public final void setTitle(String title){
		this.title = title;
	}

}
