
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Complaint;
import domain.Endorsement;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;

@Service
@Transactional
public class HandyWorkerService {

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private PhaseService			phaseService;

	@Autowired
	private ComplaintService		complaintService;
	
	@Autowired
	private FixUpTaskService		fixUpTaskService;
	
	@Autowired
	private BoxService				boxService;
	
	@Autowired
	private UserAccountService	userAccountService;
	
	
	public HandyWorkerService() {
		super();
	}

	public HandyWorker create() {
		HandyWorker result;
		result = new HandyWorker();
		final Collection<Application> applications = new ArrayList<Application>();
		final Collection<Endorsement> endorsements = new ArrayList<Endorsement>();
		UserAccount ua = this.userAccountService.create("HANDYWORKER");
		result.setApplications(applications);
		result.setUserAccount(ua);
		result.setReceivedEndorsements(endorsements);
		return result;
	}

	public HandyWorker findByPrincipal() {
		HandyWorker result = null;
		final UserAccount userAccount = LoginService.getPrincipal();
		if (userAccount == null)
			result = null;
		else
			result = this.handyWorkerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	/*
	 * Browse the catalogue of fix-up tasks and navigate to the profile of the corresponding customer,
	 * which includes his or her personal data plus his or her list of fix-up tasks.
	 */

	public Collection<FixUpTask> findAllFixUpTasks() {
		final Collection<FixUpTask> result = new ArrayList<FixUpTask>();
		result.addAll(fixUpTaskService.findAll());
		return result;
	}

	/*
	 * Filter the catalogue of fix-up tasks using the following filters: a single key word that must appear
	 * somewhere in its ticker, description, or address; a category to which the task must belong;
	 * a warranty required by the task; a range of prices; or a range of dates.
	 */
	public Collection<FixUpTask> findByFilter() {
		HandyWorker principal;
		principal = this.findByPrincipal();
		return principal.getFinder().getFixUpTasks();
	}

	/*
	 * Manage his or her applications, which includes listing them, showing them, and creating them
	 */

	// listing

	public Collection<Application> findApplicationsByHandyWorker() {
		final int id = this.findByPrincipal().getId();
		final Collection<Application> applications = new ArrayList<Application>();
		applications.addAll(this.applicationService.findByHandyWorker(id));
		return applications;
	}
	// showing them
	public Application findOneApplication(final int id) {
		Application result;
		result = this.applicationService.findById(id);
		Assert.notNull(result);
		return result;
	}

	// creating them
	public Application createApplication() {
		Application result;
		result = this.applicationService.create();
		Assert.notNull(result);
		return result;
	}

	/*
	 * When a customer accepts an application, then the corresponding handy worker can create
	 * a work plan for the corresponding fix-up task.
	 * They can fully manage the work plan, which includes showing them, creating, updating, and deleting phases.
	 */

	public Phase createPhase() {
		Phase result;
		result = this.phaseService.create();
		Assert.notNull(result);
		return result;
	}

	public Collection<Phase> findPhasesByApplication(final int id) {
		final Collection<Phase> result = new ArrayList<Phase>();
		result.addAll(this.phaseService.findPhasesByApplication(id));
		return result;
	}

	public void savePhase(final Phase phase) {
		Assert.notNull(phase);
		this.phaseService.save(phase);
	}

	public HandyWorker saveHandyWorker(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		HandyWorker result;
		boolean isNew = false;
		if (handyWorker.getId() == 0) {
			
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			handyWorker.getUserAccount().setPassword(
					passwordEncoder.encodePassword(handyWorker
							.getUserAccount().getPassword(), null));
			isNew=true;
		}
		result = this.handyWorkerRepository.save(handyWorker);
		this.handyWorkerRepository.flush();
		if(isNew){
			Finder finder = this.finderService.create2();
			finder.setHandyWorker(result);
			finder.setId(0);
		

			this.finderService.saveFinder(finder);
			this.boxService.generateDefaultFolders(result);
		}
		return result;
	}

	//Display the fix-up tasks in his or her finder.

	public Collection<FixUpTask> displayFixUpTasks() {
		final HandyWorker principal = this.findByPrincipal();
		Assert.notNull(principal);
		final Collection<FixUpTask> result = new ArrayList<FixUpTask>();
		final Finder f;
		f = this.finderService.findOneByPrincipal();
		result.addAll(f.getFixUpTasks());
		return result;
	}

	//List and show the complaints regarding the fix-up tasks in which he or she's been involved.
	//List them
	public Collection<Complaint> findInvolvedComplaints() {
		final HandyWorker principal = this.findByPrincipal();
		Assert.notNull(principal);
		return this.complaintService.findInvolvedComplaint(principal);
	}

	public HandyWorker findOne(final int handyWorkerId) {
		return this.handyWorkerRepository.findOne(handyWorkerId);
	}

	public Collection<HandyWorker> findAll() {
			Collection<HandyWorker> result = null;
			result = this.handyWorkerRepository.findAll();
			return result;
		
	}

	public void flush() {
		this.handyWorkerRepository.flush();
		
	}




}
