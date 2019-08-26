package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Section;
import domain.Tutorial;
import repositories.SectionRepository;

@Service
@Transactional
public class SectionService {

	@Autowired
	private SectionRepository sectionRepository;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private TutorialService tutorialService;

	@Autowired
	private ConferenceService conferenceService;

	@Autowired
	private UtilityService utilityService;

	public Section create(int tutorialId) {

		Administrator principal;
		Tutorial tutorial;

		tutorial = this.tutorialService.findOne(tutorialId);
		System.out.print(tutorialId);
		System.out.println(tutorial.getId());
		Assert.isTrue(!this.conferenceService.findPastConferences().contains(
				tutorial.getConference()));

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Section res;

		res = new Section();

		return res;
	}

	public Section save(Section section) {

		Section res;

		Assert.isTrue(!this.utilityService.checkUrls(section.getPictures()));
		res = this.sectionRepository.save(section);

		return res;
	}

	public Section saveCreate(Section section, int id) {
		Section res;
		Collection<Section> sections;

		Tutorial tutorial = this.tutorialService.findOne(id);
		sections = tutorial.getSections();

		res = this.sectionRepository.save(section);

		if (section.getId() == 0) {
			sections.add(res);
			tutorial.setSections(sections);
			this.tutorialService.save(tutorial);
		}

		return res;
	}

	public Section findOne(int sectionId) {

		return this.sectionRepository.findOne(sectionId);
	}

	public Collection<Section> findAll() {

		Collection<Section> result;

		result = this.sectionRepository.findAll();

		return result;
	}

	public void delete(Section section) {

		Administrator principal;
		Collection<Section> sections;
		Tutorial tutorial;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		tutorial = this.tutorialService
				.findTutorialBySectionId(section.getId());
		sections = tutorial.getSections();
		sections.remove(section);
		tutorial.setSections(sections);

		this.tutorialService.save(tutorial);

		this.sectionRepository.delete(section);
	}

	public void flush() {
		this.sectionRepository.flush();
	}
}
