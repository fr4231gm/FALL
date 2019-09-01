
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Actor;
import domain.Section;

@Service
@Transactional
public class SectionService {

	@Autowired
	private SectionRepository	sectionRepository;

	@Autowired
	private TutorialService		tutorialService;
	
	@Autowired
	private ActorService		actorService;


	public Section create() {
		final Section section = new Section();
		return section;
	}

	public Section save(final Section section) {
		Assert.notNull(section);
		Actor p = this.actorService.findByPrincipal();
		if(this.actorService.checkSpam(section.getTitle())){
			p.setIsSuspicious(true);
			this.actorService.save(p);
		}
		if(this.actorService.checkSpam(section.getText())){
			p.setIsSuspicious(true);
			this.actorService.save(p);
		}
		return this.sectionRepository.save(section);
		
	}

	public Collection<Section> findByTutorial(final int id) {
		return this.tutorialService.findOne(id).getSections();
	}

	public Collection<Section> findAll() {
		Collection<Section> result = new ArrayList<Section>();
		result = this.sectionRepository.findAll();
		return result;
	}

}
