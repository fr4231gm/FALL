package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;


public class ProviderForm extends ActorForm {

	// Atributos --------------------------------------------------------------
		private String make;

		// Getters ------------------------------------------------------------
		@NotBlank
		@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
		public String getMake() {
			return this.make;
		}

		// Setters ------------------------------------------------------------
		public void setMake(final String make) {
			this.make = make;
		}

}
