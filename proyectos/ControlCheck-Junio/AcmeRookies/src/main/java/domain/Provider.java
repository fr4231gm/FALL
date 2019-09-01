package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Provider extends Actor{
	//Atributos
	private String make;
	
	//Constructor 
	public Provider(){
		super();
	}
	
	//Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getMake(){
		return this.make;
	}
	
	//Setters
	public void setMake(final String make){
		this.make = make;
	}
	
	//Relaciones
}
