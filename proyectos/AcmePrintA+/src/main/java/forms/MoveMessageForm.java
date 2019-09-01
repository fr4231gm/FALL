package forms;

import javax.validation.constraints.NotNull;

import domain.Box;
import domain.DomainEntity;
import domain.Message;

public class MoveMessageForm extends DomainEntity {

	//Atributos ---------------------------------------------------------------
		private Box				box;
		private Box				currentbox;
		private Message 		message;
		private boolean 		isCopy;

		//Getters -----------------------------------------------------------------
		

		public boolean getIsCopy() {
			return isCopy;
		}

		@NotNull
		public Box getBox() {
			return box;
		}
		
		@NotNull
		public Message getMessage() {
			return message;
		}
		
		@NotNull
		public Box getCurrentbox() {
			return currentbox;
		}
		
		//Setters -----------------------------------------------------------------
		
		public void setBox(Box box) {
			this.box = box;
		}

		public void setIsCopy(boolean isCopy) {
			this.isCopy = isCopy;
		}


		public void setMessage(Message message) {
			this.message = message;
		}


		public void setCurrentbox(Box currentbox) {
			this.currentbox = currentbox;
		}








	}
