package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.Administrator;
import domain.Conference;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	@Autowired
	private TutorialRepository tutorialRepository;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ConferenceService conferenceService;

	public Tutorial create(int conferenceId) {
		Administrator principal;
		Conference conference;

		conference = this.conferenceService.findOne(conferenceId);
		Assert.isTrue(!this.conferenceService.findPastConferences().contains(conference));

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Tutorial res;

		res = new Tutorial();
		res.setConference(conference);

		return res;
	}

	public Tutorial save(Tutorial tutorial) {
		Tutorial res;

		res = this.tutorialRepository.save(tutorial);

		return res;
	}

	public Tutorial findOne(int tutorialId) {
		return this.tutorialRepository.findOne(tutorialId);
	}

	public Collection<Tutorial> findAll() {
		Collection<Tutorial> result;

		result = this.tutorialRepository.findAll();

		return result;
	}

	public Collection<Tutorial> findTutorialsByConferenceId(int conferenceId) {
		return this.tutorialRepository
				.findTutorialsByConferenceId(conferenceId);
	}

	public void delete(Tutorial tutorial) {
		this.tutorialRepository.delete(tutorial);
	}
}
