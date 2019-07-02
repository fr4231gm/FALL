package domain;

import javax.persistence.OneToMany;
import java.util.Collection;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.CascadeType;
import javax.persistence.Entity;

@Entity 
@Access(AccessType.PROPERTY) 
public class Tutorial extends DomainEntity {

	// Atributos
	private Collection<Section>					section;


	// Getters
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Section> getSection(){
		return this.section;
	}


	// Setters
	public void setSection(final Collection<Section> section){
		this.section = section; 
	}

}