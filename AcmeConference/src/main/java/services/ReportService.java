package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Actor;
import domain.Author;
import domain.Report;
import domain.Reviewer;
import domain.Submission;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository	reportRepository;
	
	@Autowired
	private ReviewerService 	reviewerService;
	
	@Autowired
	private SubmissionService 	submissionService;
	
	@Autowired
	private ActorService 		actorService;
	
	
	public Report create(int submissionId) {
		Report res = new Report();
		Submission submission = this.submissionService.findOne(submissionId);
		res.setSubmission(submission);
		Reviewer principal = this.reviewerService.findByPrincipal();
		Assert.notNull(principal);
		res.setReviewer(principal);
		return res;
	}

	
	public Report save(Report report){
		Reviewer principal = this.reviewerService.findByPrincipal();
		Assert.notNull(principal);
		
		Assert.isTrue(report.getReviewer().getId() == principal.getId());
		Assert.isTrue(report.getId() == 0);
		Assert.isNull(this.findBySubissionAndReviewer(principal.getId(), report.getSubmission().getId()));
		return this.reportRepository.save(report);
	}

	public Report findOne(final int reportId) {
		Report s;
		s = this.reportRepository.findOne(reportId);
		return s;
	}
	
	public Report findBySubissionAndReviewer(int reviewerId, int submissionId) {
		return this.reportRepository.findBySubissionAndReviewerId(reviewerId, submissionId);
	}

	public Collection<Report> findReportsByReviewerId(int reviewerId){
		Collection<Report> reports;
		
		reports = this.reportRepository.findReportsByReviewerId(reviewerId);
				
		return reports;		
	}
	
	public Collection<Report> findReportsByConferenceId(int conferenceId){
		Collection<Report> reports;
		
		reports = this.reportRepository.findReportsByConferenceId(conferenceId);
				
		return reports;		
	}
	
	public Collection<Report> findReportsBySubmissionId(int submissionId){
		Collection<Report> reports;
		
		reports = this.reportRepository.findReportsBySubmissionId(submissionId);
				
		return reports;		
	}

	public Collection<Report> findByPrincipal() {
		Collection<Report> reports;
		
		Reviewer principal = this.reviewerService.findByPrincipal();
		
		reports = this.reportRepository.findReportsByReviewerId(principal.getId());
				
		return reports;	
	}


	public Report findOneToDisplay(int reportId) {
		Report report = this.reportRepository.findOne(reportId);
		Actor principal = this.actorService.findByPrincipal();
		if(principal instanceof Reviewer){
			Assert.isTrue(report.getReviewer().getId() == principal.getId());
		} else if(principal instanceof Author){
			Assert.isTrue(report.getSubmission().getAuthor().getId() == principal.getId());
			Assert.isTrue(report.getSubmission().getNotified());
		}
		return report;
	}


}
