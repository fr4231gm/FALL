package forms;

import java.util.Collection;

import javax.persistence.ManyToMany;

import domain.DomainEntity;
import domain.Reviewer;
import domain.Submission;

public class SubmissionForm extends DomainEntity {
	
	//Atributos ---------------------------------------------------------------
	private Collection<Reviewer> reviewers;
	private Submission submission;
	
	
		@ManyToMany
		public Collection<Reviewer> getReviewers(){
			return this.reviewers;
		}
		
		public Submission getSubmission() {
			return this.submission;
		}
		
		
		public void setReviewers(final Collection<Reviewer> reviewers) {
			this.reviewers = reviewers;
		}
		
		public void setSubmission(final Submission submission){
			this.submission = submission;
		}
		
}
