
package services;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import domain.Actor;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class FixUpTaskService {

	@Autowired
	private FixUpTaskRepository	fixUpTaskRepository;

	@Autowired
	private CustomerService		customerService;
	
	@Autowired
	private ActorService				actorService;


	public FixUpTaskService() {
		super();
	}

	public FixUpTask create() {
		FixUpTask result;
		Collection<Application> applications = new ArrayList<Application>();
		Collection<Complaint> complaints = new ArrayList<Complaint>();
		Customer principal;
		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		result = new FixUpTask();
		Assert.notNull(result);
		result.setCustomer(principal);
		final String ticker = this.createTicker();
		Assert.notNull(ticker);
		result.setTicker(ticker);
		result.setComplaints(complaints);
		result.setApplications(applications);
		Date date = new Date(System.currentTimeMillis()-1);
		result.setMoment(date);
		return result;

	}

	public Collection<FixUpTask> findFixUpTaskPerCustomer(final Customer c) {
		Assert.notNull(c);
		return this.fixUpTaskRepository.findFixUpTaskPerCustomer(c.getId());
	}
	public Collection<FixUpTask> findAll() {
		return this.fixUpTaskRepository.findAll();
	}

	public FixUpTask findOne(final Integer id) {
		Assert.isTrue(id != 0);
		return this.fixUpTaskRepository.findOne(id);
	}

	public FixUpTask saveFixUpTask(final FixUpTask f) {
		Assert.notNull(f);
		Actor principal = this.actorService.findByPrincipal();
	    Double price = f.getMaximumPrice();
	    DecimalFormat decim = new DecimalFormat("0.00");
	    Double price2 = Double.parseDouble(decim.format(price));
	   	f.setMaximumPrice(price2);
		if(principal instanceof Customer){
			Assert.isTrue(principal.getId() == f.getCustomer().getId());
		}
		if(this.actorService.checkSpam(f.getDescription())){
			principal.setIsSuspicious(true);
			this.actorService.save(principal);
		}
		if(this.actorService.checkSpam(f.getAddress())){
			principal.setIsSuspicious(true);
			this.actorService.save(principal);
		}
		return this.fixUpTaskRepository.save(f);
	}


	private String createTicker() {
		final Date fecha = new Date();
		DateFormat df = new SimpleDateFormat("yyMMdd");
		String formattedDate = df.format(fecha);
		final String res = formattedDate + "-" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
		return res;
	}

	public Collection<Application> findApplicationFixUpTask(final int id) {
		Assert.isTrue(id != 0);
		return this.fixUpTaskRepository.findApplicationByCustomer(id);
	}

	public Collection<FixUpTask> findByCategoryId(int id) {
		return this.fixUpTaskRepository.findFixUpTasksByCategoryId(id);
	}
	
	public FixUpTask save(FixUpTask f) {
		return this.fixUpTaskRepository.save(f);
	}
	
	public void flush() {
		this.fixUpTaskRepository.flush();
	}

}
