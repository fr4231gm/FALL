package forms;

import java.util.Collection;

import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import domain.Actor;
import domain.DomainEntity;

public class MessageForm extends DomainEntity {

	//Atributos ---------------------------------------------------------------
		private String	subject;
		private String	body;
		private String	tags;

		//Getters -----------------------------------------------------------------

		@NotBlank
		@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
		public String getSubject() {
			return this.subject;
		}

		@NotBlank
		@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
		public String getBody() {
			return this.body;
		}

		@NotBlank
		@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
		public String getTags() {
			return this.tags;
		}

		//Setters -----------------------------------------------------------------

		public void setSubject(final String subject) {
			this.subject = subject;
		}

		public void setBody(final String body) {
			this.body = body;
		}

		public void setTags(final String tags) {
			this.tags = tags;
		}


		//Relations ---------------------------------------------------------------
		private Collection<Actor>	recipients;


		//Getters -----------------------------------------------------------------
		@NotEmpty
		@ManyToMany
		public Collection<Actor> getRecipients() {
			return this.recipients;
		}

		//Setters -----------------------------------------------------------------
		public void setRecipients(final Collection<Actor> recipients) {
			this.recipients = recipients;
		}

	}
