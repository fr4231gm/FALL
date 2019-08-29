
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import domain.Actor;
import domain.Administrator;
import domain.Author;
import domain.Paper;
import domain.Report;
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
	private ReportService reportService;
	
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

	public Submission create(int conferenceId) {
		final Submission s = new Submission();
		s.setAuthor(this.authorService.findByPrincipal());
		s.setConference(this.conferenceService.findOne(conferenceId));
		s.setTicker(this.generateTicker(s));
		s.setStatus("UNDER-REVIEW");
		s.setNotified(false);
		s.setMoment(new Date(System.currentTimeMillis() + 1));
		final Paper p = new Paper();
		p.setCameraReadyPaper(false);
		s.setPaper(p);

		return s;
	}
	
	public void setRev(final Submission s, final Collection<Reviewer> reviewers){
		Administrator principal;
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(this.isAssignable(s));
		
		for(Reviewer r : reviewers){
			Collection<Submission> submissions = r.getSubmissions();
			submissions.add(s);
			r.setSubmissions(submissions);
			this.reviewerService.updateSubmissions(r);
		}
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
	
	public Submission decide(final Submission submission){
		Actor principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(submission);
		int borderLine = 0, rejected = 0, accepted = 0;
		Collection<Report> reports = this.reportService.findReportsByConferenceId(submission.getConference().getId());
		
		for(Report r : reports){
			if(r.getSubmission().getStatus().equals("ACCEPTED")){
				accepted ++;
			}else if(r.getSubmission().getStatus().equals("REJECTED")){
				rejected ++;
			}else{
				borderLine ++;
			}
		}
		
		if(accepted > rejected || (accepted == rejected && borderLine != 0)){
			submission.setStatus("ACCEPTED");
		}else{
			submission.setStatus("REJECTED");
		}
		
		return this.submissionRepository.save(submission);
	}
	
	private String generateTicker(final Submission s) {
		String res = "";

		final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		for (int i = 0; i < 4; i++) {
			final int randomNumber = (int) Math.floor(Math.random() * (alphanumeric.length() - 1));
			res += alphanumeric.charAt(randomNumber);
			;
		}
		char middleName = 'X';
		final char name = s.getAuthor().getName().charAt(0);
		final char surname = s.getAuthor().getSurname().charAt(0);
		if (s.getAuthor().getMiddleName() != null){
			if (!s.getAuthor().getMiddleName().isEmpty()){
				middleName = s.getAuthor().getMiddleName().charAt(0);
			}
		}

		// Adding formatted date to alphanumeric code
		res = String.valueOf(name).toUpperCase()  + String.valueOf(middleName).toUpperCase() + String.valueOf(surname).toUpperCase() + "-" + res;

		return res;
	}
	
	public SubmissionForm construct(Submission submission) {
		
		SubmissionForm submissionForm = new SubmissionForm();
		
		submissionForm.setId(submission.getId());
		submissionForm.setVersion(submission.getVersion());
		submissionForm.setReviewers(reviewerService.findReviewersBySubmission(submission.getId()));
		
		return submissionForm;
	}

	public Submission findSubmissionByPaperTitle(String title) {
		return this.submissionRepository.findSubmissionByPaperTitle(title);
	}

	public Collection<Submission> findReportablesSubmissions() {
		Reviewer principal = this.reviewerService.findByPrincipal();
		return this.submissionRepository.findReportablesSubmissions(principal.getId());
	}

	public Boolean isAssignable(Submission submission) {
		boolean res = false;
		//Para poder asignar reviewers a una submission, tiene que estar en fecha, estar bajo revisión y no tener ningún reviewer asignado
		if (submission.getConference().getNotificationDeadline().after(new Date()) && submission.getStatus().equals("UNDER-REVIEW") && this.reviewerService.findBySubmission(submission.getId()).isEmpty()){
			res = true;
		}
		return res;
	}

	public Boolean canDecide(Submission submission) {
		boolean res = false;
		
		//Para poder cambiar el estado de una submission, tiene que estar en fecha, estar bajo revisión y tener al menos un reviewer asignado
		if(submission.getConference().getSubmissionDeadline().before(new Date()) && submission.getStatus().equals("UNDER-REVIEW") && !this.reviewerService.findBySubmission(submission.getId()).isEmpty()){
			res = true;
		}
		return res;
	}

	public Boolean isNotificable(Submission submission) {
		boolean res = false;
		
		//Para poder notifcar la actualización de estado de una submission, no debe haberse notifcado con anterioridad, debe estár en fecha, y la decisión del estado debe haber sido tomada
		if(!submission.getNotified() && submission.getConference().getNotificationDeadline().after(new Date()) && !submission.getStatus().equals("UNDER-REVIEW")){
			res = true;
		}
		return res;
	}
	
	public Collection<Paper> findCameraReadyPapersByAuthorId(int authorId){
		return this.submissionRepository.findCameraReadyPapersByAuthorId(authorId);
	}

}
