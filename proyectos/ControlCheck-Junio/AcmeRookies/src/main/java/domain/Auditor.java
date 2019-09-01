package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
//--import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
//--import javax.persistence.AccessType;
public class Auditor extends Actor{
	private Collection<Position>	positions;
	//Atributos - no tiene
	//Constructor----------------------------------------------------
	public Auditor(){
		super();
	}
	
	//Relaciones
	//Getters
	@ManyToMany()
	public Collection<Position> getPositions() {
		return positions;
	}


	//Setters
	public void setPositions(Collection<Position> positions) {
		this.positions = positions;
	}

}
