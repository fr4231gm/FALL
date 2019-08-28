package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Administrator;
import domain.Section;
import domain.Tutorial;
import forms.SectionForm;

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

		Assert.isTrue(!this.conferenceService.findPastConferences().contains(
				tutorial.getConference()));

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Section res;

		res = new Section();

		return res;
	}

	public Section save(Section section, int id) {
		Section res;
		Collection<Section> sections;

		Assert.isTrue(!this.utilityService.checkUrls(section.getPictures()));
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
	
	public Section reconstruct(SectionForm sectionForm) {
		Section res;
		if (sectionForm.getId() != 0) {
			res = this.findOne(sectionForm.getId());
		} else {
			res = new Section();
			res.setVersion(sectionForm.getVersion());
			res.setId(sectionForm.getId());
		}

		res.setTitle(sectionForm.getTitle());
		res.setSummary(sectionForm.getSummary());
		res.setPictures(sectionForm.getPictures());

		return res;
	}
	
	public SectionForm construct(final Section section) {
		final SectionForm res = new SectionForm();

		res.setId(section.getId());
		res.setVersion(section.getVersion());
		res.setTitle(section.getTitle());
		res.setSummary(section.getSummary());
		res.setPictures(section.getPictures());
		res.setTutorial(this.tutorialService.findTutorialBySectionId(section.getId()));

		return res;
	}
}
