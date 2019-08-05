
package services;

import java.util.Collection;

import javax.persistence.Inheritance;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
@Inheritance
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository			actorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuthorService			authorService;
	
	@Autowired
	private ReviewerService			reviewerService;
	
	@Autowired
	private SponsorService			sponsorService;

	// Simple CRUDs methods ---------------------------------------------------

	public Collection<Actor> findAll() {

		Collection<Actor> result;

		result = this.actorRepository.findAll();

		return result;

	}

	public Actor findOne(final int actorId) {
		Actor result;

		result = this.actorRepository.findOne(actorId);
		if (result == null) {
			result = this.authorService.findOne(actorId);
			if (result == null) {
				result = this.reviewerService.findOne(actorId);
				if (result == null)
					result = this.sponsorService.findOne(actorId);
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

	public Actor findActorByUsername(final String username) {
		Assert.notNull(username);
		final Actor result;
		result = this.actorRepository.findActorByUsername(username);
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

	// UPDATE actors logged as administrator
	public void update(final Actor actor) {
		this.actorRepository.save(actor);
	}
	//Borrar actor
	public void delete(final Actor principal) {
		Assert.notNull(principal);
		this.actorRepository.delete(principal);
	}
}
