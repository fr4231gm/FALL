package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Report;

import repositories.ReportRepository;



@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository	reportRepository;
	
	public Collection<Report> findAll() {
		Collection<Report> result;

		result = this.reportRepository.findAll();

		return result;

	}

	public Report findOne(final int reportId) {
		Report s;
		s = this.reportRepository.findOne(reportId);
		return s;
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
}
