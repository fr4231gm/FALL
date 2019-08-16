package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import domain.Activity;
import domain.Conference;

public class CommentForm {

	// Atributos
	private int 					id;
	private int 					version;
	private String					title;
	private String					author;
	private String					text;
	private Conference				conference;
	private Activity				activity;


	// Getters
	public int getId() {
		return this.id;
	}

	public int getVersion() {
		return this.version;
	}
	
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getTitle(){
		return this.title;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getAuthor(){
		return this.author;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getText(){
		return this.text;
	}
	
	public Conference getConference(){
		return this.conference;
	}
	
	public Activity getActivity(){
		return this.activity;
	}
	
	// Setters
	public void setTitle(final String title){
		this.title = title; 
	}

	public void setAuthor(final String author){
		this.author = author; 
	}

	public void setText(final String text){
		this.text = text; 
	}
	
	public void setConference(final Conference conference){
		this.conference = conference; 
	}
	
	public void setActivity(final Activity activity){
		this.activity = activity; 
	}
	
	public void setId(final int id) {
		this.id = id;
	}

	public void setVersion(final int version) {
		this.version = version;
	}
}
