
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.LoginService;
import security.UserAccount;

import domain.Actor;
import domain.Administrator;
import domain.Complaint;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RefereeRepository	refereeRepository;


	// Supporting services ----------------------------------------------------
	@Autowired
	private ComplaintService	complaintService;
	
	@Autowired
	private BoxService	boxService;
	
	@Autowired
	private ActorService	actorService;
	
	@Autowired
	private UserAccountService	userAccountService;
	

	// Constructors -----------------------------------------------------------

	public RefereeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Referee create() {
		// Checking permissions
		Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		Referee result = new Referee();
		final UserAccount userAccount = this.userAccountService.create("REFEREE");
		this.userAccountService.save(userAccount);
		result.setUserAccount(userAccount);
		return result;
	}

	public Referee findOne(final int refereeId) {
		Referee result = null;
		result = this.refereeRepository.findOne(refereeId);
		return result;
	}

	public Collection<Referee> findAll() {
		Collection<Referee> result = null;
		result = this.refereeRepository.findAll();
		return result;
	}

	public Referee save(final Referee referee) {
		Assert.notNull(referee);
		Referee result;
		boolean isNew = false;
		if (referee.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			referee.getUserAccount().setPassword(passwordEncoder.encodePassword(referee.getUserAccount().getPassword(), null));
			isNew=true;
		}
		result = this.refereeRepository.save(referee);
		if(isNew){
			this.boxService.generateDefaultFolders(result);
		}
		return result;
	}

	public void delete(final int id) {
		this.refereeRepository.delete(id);
	}

	// Other business methods -------------------------------------------------

	public Collection<Complaint> findNoSelfAssigned() {
		Collection<Complaint> result = null;
		
		result = this.complaintService.findNoSelfAssigned();
		return result;
	}

	public Collection<Complaint> findSelfAssigned() {
		Collection<Complaint> result = null;
		Referee principal = this.findByPrincipal();
		result = this.complaintService.findSelfAssigned(principal.getId());
		return result;
	}
	
	
	public Referee findByPrincipal() {
		Referee result = null;
		final UserAccount userAccount = LoginService.getPrincipal();
		if (userAccount == null)
			result = null;
		else
			result = this.refereeRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public void flush() {
		this.refereeRepository.flush();
		
	}
	
}
