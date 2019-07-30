package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Author;
import domain.Registration;

import repositories.RegistrationRepository;

@Service
@Transactional
public class RegistrationService {

	// Repositorios
	@Autowired
	private RegistrationRepository registrationRepository;

	// Servicios
	@Autowired
	private AuthorService authorService;

	@Autowired
	private ConferenceService conferenceService;

	// Constructors ------------------------------------

	public RegistrationService() {
		super();
	}

	public Registration create(final int conferenceId) {

		Assert.notNull(conferenceId);
		Registration result;
		Author principal;

		principal = this.authorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Registration();
		result.setAuthor(principal);
		result.setConference(this.conferenceService.findOne(conferenceId));

		Assert.notNull(result.getConference());

		return result;
	}

	public Registration save(final Registration registration) {
		Assert.notNull(registration);

		Registration res = null;
		Author principal;

		principal = this.authorService.findByPrincipal();
		Assert.isTrue(registration.getAuthor().getId() == principal.getId());

		
		// Check if it's an updating
		if (registration.getId() == 0) {

			if (principal instanceof Author){
				Assert.isTrue(registration.getAuthor().getId() == principal.getId());

			// Calling the repository
				res = this.registrationRepository.save(registration);
			}
			
		} else {
			
			// Calling the repository
			res = this.registrationRepository.save(registration);
		
		}
		
		return res;
	}
		

	public Registration findOne(final int registrationId) {

		final Registration reg = this.registrationRepository
				.findOne(registrationId);

		return reg;
	}

	public Collection<Registration> findAll() {
		Collection<Registration> result;

		result = this.registrationRepository.findAll();

		return result;

	}

}
