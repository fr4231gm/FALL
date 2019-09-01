
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Actor;
import domain.Complaint;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;

@Service
@Transactional
public class ComplaintService {

	@Autowired
	private ComplaintRepository	complaintRepository;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ActorService		actorService;
	
	public ComplaintService() {
		super();
	}

	public Complaint create() {
		Complaint result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		result = new Complaint();
		result.setCustomer(principal);
		final String ticker = this.createTicker();
		result.setTicker(ticker);
		result.setMoment(new Date(System.currentTimeMillis() - 1));
		result.setIsDraft(true);

		return result;
	}

	private String createTicker() {

		final Date fecha = new Date();
		final String res = fecha.toString() + RandomStringUtils.randomAlphanumeric(6).toUpperCase();

		return res;
	}

	public Collection<Complaint> findAllComplaints() {
		Collection<Complaint> res = new ArrayList<Complaint>();
		res.addAll(this.complaintRepository.findAll());
		return res;

	}

	public Collection<Complaint> findComplaintsWithoutReffere() {
		return this.complaintRepository.findWithoutReferee();

	}

	public Collection<Complaint> findSelfAssignedComplaints(final Referee r) {
		Assert.notNull(r);
		return this.complaintRepository.findSelfAssignedComplaints(r.getId());
	}

	public Collection<Complaint> findInvolvedComplaint(final HandyWorker h) {
		Assert.notNull(h);
		return this.complaintRepository.findInvolvedComplaint(h.getId());
	}

	public Complaint findOneComplaint(final Integer id) {
		Assert.isTrue(id != 0);
		return this.complaintRepository.findOne(id);
	}

	public Complaint saveComplaint(final Complaint c) {
		Assert.notNull(c);
		Assert.isTrue(c.getId()==0);
		Actor principal = this.actorService.findByPrincipal();
		if(this.actorService.checkSpam(c.getDescription())){
			principal.setIsSuspicious(true);
			this.actorService.save(principal);
		}
		return this.complaintRepository.save(c);
	}

	public Collection<Complaint> findCustomerComplaints(final Customer principal) {
		Assert.notNull(principal);
		return this.complaintRepository.findCustomerComplaints(principal.getId());
	}

	public Collection<Complaint> findNoSelfAssigned() {
		Collection<Complaint> result = new ArrayList<Complaint>();
		result = this.complaintRepository.findWithoutReferee();
		return result;
	}

	public Collection<Complaint> findSelfAssigned(int id) {
		Collection<Complaint> result = new ArrayList<Complaint>();
		result = this.complaintRepository.findAssignedComplaints(id);
		return result;
	}

}
