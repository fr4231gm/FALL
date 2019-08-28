package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity 
@Access(AccessType.PROPERTY) 
public class Category extends DomainEntity {

	// Atributos
	private Map<String, String>			name;
	private Category					parentCategory;


	// Getters
	@ElementCollection
	@NotNull
	public Map<String, String> getName(){
		return this.name;
	}

	@ManyToOne
	public Category getParentCategory(){
		return this.parentCategory;
	}

	// Setters
	public void setName(final Map<String, String> name){
		this.name = name; 
	}

	public void setParentCategory(final Category parentCategory){
		this.parentCategory = parentCategory; 
	}

}