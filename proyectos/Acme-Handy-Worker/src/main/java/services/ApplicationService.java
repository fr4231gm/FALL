
package services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Actor;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	applicationRepository;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private CustomerService			customerService;
	
	@Autowired
	private ActorService			actorService;
	
	@Autowired
	private FixUpTaskService		fixUpTaskService;


	public Application create() {
		Application result;
		result = new Application();
		HandyWorker principal;
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		result.setHandyWorker(principal);
		result.setStatus("PENDING");
		result.setMoment(new Date(System.currentTimeMillis()-1));
		return result;
	}

	public Collection<Application> findByHandyWorker(final int id) {
		final Collection<Application> result = new ArrayList<Application>();
		result.addAll(this.applicationRepository.findByHandyWorker(id));
		return result;
	}

	public Application findById(final int id) {
		Application result;
		result = this.applicationRepository.findById(id);
		return result;
	}

	public Application save(final Application a) {
		Assert.notNull(a);
		Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
	    Double price = a.getPrice();
	    DecimalFormat decim = new DecimalFormat("0.00");
	    Double price2 = Double.parseDouble(decim.format(price));
	    a.setPrice(price2);
		if(!(a.getComments().equals(this.findById(a.getId())))&& this.actorService.checkSpam(a.getComments())){
			principal.setIsSuspicious(true);
			if(principal instanceof Customer){
				this.customerService.save((Customer)principal);
			}
			if(principal instanceof HandyWorker){
				this.handyWorkerService.saveHandyWorker((HandyWorker)principal);
			}
		}
		Application result;
		if (a.getStatus().equals("ACCEPTED")){
			Assert.notNull(a.getCreditCard());
		}
		final FixUpTask f = a.getFixUpTask();
		if (!f.getApplications().contains(a)) {
			final Collection<Application> apps = f.getApplications();
			apps.add(a);
			f.setApplications(apps);
			this.fixUpTaskService.saveFixUpTask(f);
		}

		final HandyWorker h = a.getHandyWorker();
		if (!h.getApplications().contains(a)) {
			final Collection<Application> apps = h.getApplications();
			apps.add(a);
			h.setApplications(apps);
			this.handyWorkerService.saveHandyWorker(h);
		}

		result = this.applicationRepository.save(a);
		Assert.notNull(result);
		return result;
	}
	
	public Collection<Application> findAll() {
		final Collection<Application> result = new ArrayList<Application>();
		result.addAll(this.applicationRepository.findAll());
		return result;
	}
	
	public void notify(Customer c, HandyWorker h){
		this.messageService.applicationStatusChange(c.getId(), h.getId());
	}

	public void flush(){
		this.applicationRepository.flush();
	}


}
