
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SubmissionRepository;
import domain.Actor;
import domain.Author;
import domain.Paper;
import domain.Reviewer;
import domain.Submission;
import forms.SubmissionForm;

@Service
@Transactional
public class SubmissionService {

	@Autowired
	private SubmissionRepository	submissionRepository;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private ConferenceService		conferenceService;
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private ReviewerService reviewerService;
	
	@Autowired
	private Validator	validator;


	public Collection<Submission> findByAuthor() {
		final Author a = this.authorService.findByPrincipal();
		Assert.notNull(a);

		final Collection<Submission> subs = this.submissionRepository.findByAuthorId(a.getId());

		return subs;
	}
	
	public Collection<Submission> findAll() {
		Collection<Submission> result;

		result = this.submissionRepository.findAll();

		return result;

	}

	public Submission findOne(final int submissionId) {
		Submission s;
		s = this.submissionRepository.findOne(submissionId);
		return s;
	}

	public Submission create(final Integer conferenceId) {
		final Submission s = new Submission();
		s.setAuthor(this.authorService.findByPrincipal());
		s.setConference(this.conferenceService.findOne(conferenceId));
		s.setTicker(this.generateTicker(s));
		s.setStatus("UNDER-REVIEW");
		s.setMoment(new Date(System.currentTimeMillis() + 1));
		final Paper p = new Paper();
		p.setCameraReadyPaper(false);
		s.setPaper(p);

		return s;
	}
	
	public Submission save(final Submission s) {
		Submission result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		if (principal instanceof Author) {
			Assert.isTrue(s.getAuthor().getId() == this.authorService.findByPrincipal().getId());
		}else{
			Assert.isTrue(s.getConference().getAdministrator().getId() == this.administratorService.findByPrincipal().getId());
		}
		
		final Date actual = new Date(System.currentTimeMillis() - 1);
		Assert.isTrue(s.getConference().getSubmissionDeadline().after(actual));
		s.setMoment(actual);
		result = this.submissionRepository.save(s);
		return result;
	}
	
	public Submission saveAssign(final SubmissionForm submissionForm) {
		Submission result;
		Actor principal;
		Collection<Reviewer> reviewers;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		reviewers = this.reviewerService.findAll();
		
		if (principal instanceof Author) {
			Assert.isTrue(submissionForm.getAuthor().getId() == this.authorService.findByPrincipal().getId());
		}else{
			Assert.isTrue(submissionForm.getConference().getAdministrator().getId() == this.administratorService.findByPrincipal().getId());
		}
		
		submissionForm.setReviewers(reviewers);
		result = this.submissionRepository.save(this.findOne(submissionForm.getId()));
		return result;
	}
	
	public Submission saveAutoassign(final SubmissionForm submissionForm) {
		Submission result;
		Actor principal;
		Collection<Reviewer> reviewers;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		reviewers = this.conferenceService.getCompatibleReviewers(submissionForm.getConference());
		
		if (principal instanceof Author) {
			Assert.isTrue(submissionForm.getAuthor().getId() == this.authorService.findByPrincipal().getId());
		}else{
			Assert.isTrue(submissionForm.getConference().getAdministrator().getId() == this.administratorService.findByPrincipal().getId());
		}
		
		submissionForm.setReviewers(reviewers);
		result = this.submissionRepository.save(this.findOne(submissionForm.getId()));
		return result;
	}
	
	private String generateTicker(final Submission s) {
		String res = "";

		final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		for (int i = 0; i < 4; i++) {
			final int randomNumber = (int) Math.floor(Math.random() * (alphanumeric.length() - 1));
			res += alphanumeric.charAt(randomNumber);
			;
		}
		String middleName;
		final String name = s.getAuthor().getName();
		if (s.getAuthor().getMiddleName() != null)
			middleName = s.getAuthor().getMiddleName();
		else
			middleName = "X";

		final String surname = s.getAuthor().getSurname();

		// Adding formatted date to alphanumeric code
		res = name.substring(0, 1) + middleName.substring(0, 1) + surname.substring(0, 1) + "-" + res;

		return res;
	}
	
	public SubmissionForm construct(Submission submission) {
		
		SubmissionForm submissionForm = new SubmissionForm();
		
		submissionForm.setId(submission.getId());
		submissionForm.setVersion(submission.getVersion());
		submissionForm.setTicker(submission.getTicker());
		submissionForm.setMoment(submission.getMoment());
		submissionForm.setStatus(submission.getStatus());
		submissionForm.setPaper(submission.getPaper());
		submissionForm.setConference(submission.getConference());
		submissionForm.setAuthor(submission.getAuthor());
		submissionForm.setReviewers(reviewerService.findReviewersBySubmission(submission.getId()));
		
		return submissionForm;
	}

	public Submission reconstruct(final SubmissionForm submissionForm, final BindingResult binding) {
		
		final Submission submission = new Submission();
		
		if (submissionForm.getId() != 0){
			
			submission.setConference(submissionForm.getConference());
			submission.setAuthor(submissionForm.getAuthor());
			
		} 		
			submission.setId(submissionForm.getId());
			submission.setVersion(submissionForm.getVersion());
			submission.setTicker(submissionForm.getTicker());
			submission.setMoment(submissionForm.getMoment());
			submission.setStatus(submissionForm.getStatus());
			submission.setPaper(submissionForm.getPaper());
		
		// Validar formulario
		this.validator.validate(submissionForm, binding);
				
		return submission;

	}
	
	public Submission findSubmissionByPaperTitle(String title) {
		return this.submissionRepository.findSubmissionByPaperTitle(title);
	}

}
