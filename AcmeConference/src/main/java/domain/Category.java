package domain;

import javax.persistence.ManyToOne;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Entity;

@Entity 
@Access(AccessType.PROPERTY) 
public class Category extends DomainEntity {

	// Atributos
	private String					name;
	private String					nombre;
	private Category					parentcategory;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getName(){
		return this.name;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getNombre(){
		return this.nombre;
	}

	@ManyToOne
	public Category getParentcategory(){
		return this.parentcategory;
	}



	// Setters
	public void setName(final String name){
		this.name = name; 
	}

	public void setNombre(final String nombre){
		this.nombre = nombre; 
	}

	public void setParentcategory(final Category parentcategory){
		this.parentcategory = parentcategory; 
	}

}