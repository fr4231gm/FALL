package forms;

import domain.DomainEntity;
import domain.Position;

public class EnrolmentForm extends DomainEntity {

	//Atributos ----------------------------------------
	private int 					id;
	private Position 				position;


	//Getters -----------------------------------------------------------------
	
	public int getId() {
		return this.id;
	}

	public Position getPosition() {
		return this.position;
	}

	//Setters -----------------------------------------------------------------
	public void setId(int id) {
		this.id = id;
	}
	public void setPosition(Position position) {
		this.position = position;
	}

}
