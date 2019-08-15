package forms;

import java.util.Collection;

import javax.persistence.ManyToMany;

import domain.DomainEntity;
import domain.Reviewer;

public class SubmissionForm extends DomainEntity {
	
	//Atributos ---------------------------------------------------------------
	private Collection<Reviewer> reviewers;
	
		@ManyToMany
		public Collection<Reviewer> getReviewers(){
			return this.reviewers;
		}
		
		
		public void setReviewers(final Collection<Reviewer> reviewers) {
			this.reviewers = reviewers;
		}	
		
}
