package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PresentationRepository;
import domain.Administrator;
import domain.Conference;
import domain.Presentation;

@Service
@Transactional
public class PresentationService {

	@Autowired
	private PresentationRepository presentationRepository;
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private ConferenceService conferenceService;

	public Presentation create(int conferenceId) {
		Administrator principal;
		Conference conference;
		
		conference = this.conferenceService.findOne(conferenceId);
		Assert.isTrue(!this.conferenceService.findPastConferences().contains(conference));
		
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Presentation res;

		res = new Presentation();
		res.setConference(conference);

		return res;
	}

	public Presentation save(Presentation presentation) {
		Presentation res;

		res = this.presentationRepository.save(presentation);

		return res;
	}

	public Presentation findOne(int presentationId) {
		return this.presentationRepository.findOne(presentationId);
	}

	public Collection<Presentation> findAll() {
		Collection<Presentation> result;

		result = this.presentationRepository.findAll();

		return result;
	}

	public Collection<Presentation> findPresentationsByConferenceId(
			int conferenceId) {
		return this.presentationRepository
				.findPresentationsByConferenceId(conferenceId);
	}

	public void delete(Presentation presentation) {
		this.presentationRepository.delete(presentation);
	}
}
