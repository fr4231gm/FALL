
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Inheritance;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Auditor;
import domain.Company;
import domain.Configuration;
import domain.Provider;
import domain.Rookie;

@Service
@Transactional
@Inheritance
public class ActorService {

	// Managed repository -----------------

	@Autowired
	private ActorRepository			actorRepository;

	// Supporting services ----------------

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private ProviderService			providerService;

	@Autowired
	private ConfigurationService	configurationService;


	//@Autowired
	//private MessageService			messageService;

	// Constructors ------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUDs methods ------------------------

	public Collection<Actor> findAll() {

		Collection<Actor> result;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Actor findOne(final int actorId) {
		Actor result;

		result = this.actorRepository.findOne(actorId);
		if (result == null) {
			result = this.companyService.findOne(actorId);
			if (result == null) {
				result = this.rookieService.findOne(actorId);
				if (result == null)
					result = this.administratorService.findOne(actorId);
			}
		}
		Assert.notNull(result);
		return result;
	}

	// Other business methods ---------------------------

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		final Actor result;

		result = this.actorRepository.findActorByUserAccountId(userAccount.getId());

		return result;

	}

	public Collection<Actor> findAllExceptPrincipal() {
		Collection<Actor> result;
		Actor principal;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		result.remove(principal);
		return result;
	}

	public Actor save(final Actor principal) {
		Assert.notNull(principal);
		return this.actorRepository.save(principal);

	}

	public boolean checkSpam(final String s) {
		boolean isSpam = false;
		final List<Configuration> c1 = new ArrayList<Configuration>(this.configurationService.findAll());
		for (int i = 0; i < c1.size(); i++) {
			final Configuration c = c1.get(i);
			String y = c.getSpamWords().replaceAll(" ", "");
			y = y.toLowerCase();
			final String aux = s.toLowerCase();
			final String[] spam = y.split(",");
			for (final String e : spam)
				if (aux.contains(e))
					isSpam = true;
		}
		return isSpam;
	}

	public Collection<Integer> countTotalPositiveAndNegativeWordsPerActor(final Actor actor) {
		Assert.notNull(actor);
		final Integer p = 0;
		final Integer n = 0;
		final List<Integer> posNegWords = new ArrayList<Integer>();
		/*
		 * final Collection<Message> messages = this.messageService.findSended(actor.getId());
		 * for (final Message m : messages) {
		 * final List<Integer> l = new ArrayList<Integer>(this.messageService.positiveAndNegativeWords(m));
		 * p += l.get(0);
		 * n += l.get(1);
		 * }
		 */
		posNegWords.add(p);
		posNegWords.add(n);
		return posNegWords;
	}

	// Check if an actor can be considered as spammer
	public Boolean hasSpamMessages(final int actorId) {
		this.administratorService.checkPrincipal();

		Boolean res = false;
		Collection<Boolean> aux = new ArrayList<>();
		aux = this.actorRepository.checkActorHasSpamMessages(actorId);
		if (aux.contains(true))
			res = true;
		return res;
	}

	public String deleteByUserDropOut() {
		String res = "";
		final Actor principal = this.findByPrincipal();
		if (principal instanceof Auditor)
			res = this.auditorService.deleteUserAccount();
		else if (principal instanceof Company)
			res = this.companyService.deleteUserAccount();
		else if (principal instanceof Rookie)
			res = this.rookieService.deleteUserAccount();
		else if (principal instanceof Provider)
			res = this.providerService.deleteUserAccount();

		return res;

	}
	public String exportData() {
		final Actor principal = this.findByPrincipal();
		String res = "";
		if (principal instanceof Auditor)
			res = this.auditorService.exportData();
		else if (principal instanceof Company)
			res = this.companyService.exportData();
		else if (principal instanceof Rookie)
			res = this.rookieService.exportData();
		else if (principal instanceof Provider)
			res = this.providerService.exportData();
		return res;
	}

	// UPDATE actors logged as administrator
	public void update(final Actor actor) {
		this.actorRepository.save(actor);
	}

}
