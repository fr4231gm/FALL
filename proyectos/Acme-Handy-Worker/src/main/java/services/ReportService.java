
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReportRepository;

import domain.Customer;
import domain.Note;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ReportRepository	reportRepository;
	
	@Autowired
	private ActorService		actorService;
	
	@Autowired
	private CustomerService		customerService;

	// Constructors -----------------------------------------------------------

	public ReportService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Report create() {

		Report result = null;
		result = new Report();

		result.setNotes(new ArrayList<Note>());
		result.setIsDraft(true);
		result.setMoment(new Date(System.currentTimeMillis()-1));
		return result;
	}

	public Report findOne(final int reportId) {

		Report result = null;
		result = this.reportRepository.findOne(reportId);
		return result;

	}

	public Collection<Report> findAll() {

		Collection<Report> result = null;
		result = this.reportRepository.findAll();
		return result;
		
	}

	public Report save(final Report report) {
		Assert.notNull(report);
		Assert.isTrue(!report.getComplaint().getIsDraft());
		Customer c = this.customerService.findByPrincipal();
		if(this.actorService.checkSpam(report.getAttachments())){
			c.setIsSuspicious(true);
			this.customerService.save(c);
		}
		if(this.actorService.checkSpam(report.getDescription())){
			c.setIsSuspicious(true);
			this.customerService.save(c);
		}
		Report result = this.reportRepository.save(report);
		return result;
	}

	public void delete(final int id) {
		Assert.isTrue(this.reportRepository.findOne(id).getIsDraft());
		this.reportRepository.delete(id);
	}

}
