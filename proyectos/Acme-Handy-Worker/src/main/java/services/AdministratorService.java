
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.Endorser;
import domain.HandyWorker;

@Service
@Transactional
public class AdministratorService {

	//	Managed repository
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services
	@Autowired
	private BoxService				boxService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private EndorserService			endorserService;

	@Autowired
	private UserAccountService		userAccountService;


	//	Constructor	
	public AdministratorService() {
		super();
	}

	// CRUD methods

	public Administrator create() {
		// Checking permissions
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		final Administrator admin = new Administrator();
		final UserAccount userAccount = this.userAccountService.create("ADMIN");
		admin.setUserAccount(userAccount);
		// Creating new entity
		admin.setIsSuspicious(false);

		return admin;
	}

	public Administrator save(final Administrator administrator) {
		// Checking parameter
		Assert.notNull(administrator);
		final Actor principal = this.actorService.findByPrincipal();
		Administrator result;
		Assert.isTrue(principal instanceof Administrator);
		boolean isNew = false;
		if (administrator.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			administrator.getUserAccount().setPassword(passwordEncoder.encodePassword(administrator.getUserAccount().getPassword(), null));
			isNew = true;
		}
		result = this.administratorRepository.save(administrator);
		if (isNew)
			this.boxService.generateDefaultFolders(result);

		return result;

	}

	public Collection<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	// Suspicious actors related methods

	public Collection<Actor> findAllSuspiciousActors() {
		// Checking permissions
		final UserAccount ua = LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(Authority.ADMIN));

		final Collection<Actor> suspicious = new HashSet<>();
		suspicious.addAll(this.actorService.findAllSuspiciousActors());
		return suspicious;
	}

	public void banHammer(final Actor actor) {
		// Checking parameter
		Assert.notNull(actor);

		// Checking permissions
		final UserAccount ua = LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(Authority.ADMIN));
		Assert.isTrue(actor.getIsSuspicious());

		if (actor.getUserAccount().isEnabled())
			actor.getUserAccount().setEnabled(false);
		else
			actor.getUserAccount().setEnabled(true);
	}

	// Score computing method
	public Double computeScore(final Endorser endorser) {
		// Checking parameter
		Assert.notNull(endorser);

		// Checking permissions
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(Authority.ADMIN));

		final List<Integer> l = new ArrayList<Integer>(this.endorserService.countTotalPositiveAndNegativeWordsPerEndorser(endorser));
		final Double p = (double) l.get(0);
		final Double n = (double) l.get(1);

		final Double score = (p - n) / (p + n);
		return score;
	}

	public Collection<Object[]> findFixUpTaskPerUserStats() {
		return this.administratorRepository.findFixUpTaskPerUserStats();
	}
	public Collection<Object[]> findApplicationPerFixUpTaskStats() {
		return this.administratorRepository.findApplicationPerFixUpTaskStats();
	}
	public Collection<Object[]> findMaxPricePerFixUpTaskStats() {
		return this.administratorRepository.findMaxPricePerFixUpTaskStats();
	}
	public Collection<Object[]> findPricePerApplicationStats() {
		return this.administratorRepository.findPricePerApplicationStats();
	}
	public Double findPendingApplications() {
		return this.administratorRepository.findPendingApplications();
	}
	public Double findRatioOfAcceptedApplications() {
		return this.administratorRepository.findRatioOfAcceptedApplications();
	}
	public Double findRatioOfRejectedApplications() {
		return this.administratorRepository.findRatioOfRejectedApplications();
	}
	public Double findRatioOfPendingApplications() {
		return this.administratorRepository.findRatioOfPendingApplications();
	}
	public Collection<Customer> findCustomerFixUpTasksAboveAverage() {
		return this.administratorRepository.findCustomerFixUpTasksAboveAverage();
	}
	public Collection<HandyWorker> findHandyWorkersApplicationAboveAverage() {
		return this.administratorRepository.findHandyWorkersApplicationAboveAverage();
	}
	public Collection<Double> findComplaintStats() {
		return this.administratorRepository.findComplaintStats();
	}
	public Collection<Double> findNumberOfNotesPerRefereeStats() {
		return this.administratorRepository.findNumberOfNotesPerRefereeStats();
	}
	public Double findRatioOfFixUpTaskWithComplaint() {
		return this.administratorRepository.findRatioOfFixUpTaskWithComplaint();
	}
	public Collection<Customer> findTopThreeCustomerByComplaints() {
		final List<Customer> aux = new ArrayList<Customer>(this.administratorRepository.findTopThreeCustomerByComplaints());
		final Collection<Customer> res = new ArrayList<Customer>();
		for (int i = 0; i < 3; i++)
			res.add(aux.get(i));
		return res;
	}
	public Collection<HandyWorker> findTopThreeHandyWorkerByComplaints() {
		final List<HandyWorker> aux = new ArrayList<HandyWorker>(this.administratorRepository.findTopThreeHandyWorkerByComplaints());
		final Collection<HandyWorker> res = new ArrayList<HandyWorker>();
		for (int i = 0; i < 3; i++)
			res.add(aux.get(i));
		return res;
	}

	public void flush() {
		this.administratorRepository.flush();
	}

}
