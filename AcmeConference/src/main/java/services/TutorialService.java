package services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.Administrator;
import domain.Conference;
import domain.Section;
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

	@Autowired
	private UtilityService utilityService;

	@Autowired
	private ActivityService activityService;

	public Tutorial create(int conferenceId) {
		Administrator principal;
		Conference conference;

		conference = this.conferenceService.findOne(conferenceId);
		Assert.isTrue(!this.conferenceService.findPastConferences().contains(
				conference));

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Tutorial res;

		res = new Tutorial();
		res.setConference(conference);
		res.setSections(Collections.<Section> emptyList());

		return res;
	}

	public Tutorial save(Tutorial tutorial) {
		Tutorial res;
		
		Assert.isTrue(!this.utilityService.checkUrls(tutorial.getAttachments()));
		Assert.isTrue(this.activityService.checkStartMoment(tutorial));
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

	public Tutorial findTutorialBySectionId(int sectionId) {
		return this.tutorialRepository.findTutorialBySectionId(sectionId);
	}

	public void delete(Tutorial tutorial) {
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		this.tutorialRepository.delete(tutorial);
	}

	public void flush() {
		this.tutorialRepository.flush();
	}

}
