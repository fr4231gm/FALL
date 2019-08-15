package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity 
@Access(AccessType.PROPERTY) 
public class Tutorial extends Activity {

	// Atributos
	private Collection<Section>					sections;


	// Getters
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Section> getSections(){
		return this.sections;
	}


	// Setters
	public void setSections(final Collection<Section> sections){
		this.sections = sections; 
	}

}