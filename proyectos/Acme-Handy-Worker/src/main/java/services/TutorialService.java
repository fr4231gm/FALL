
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.Actor;
import domain.HandyWorker;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	@Autowired
	private TutorialRepository	tutorialRepository;

	@Autowired
	private HandyWorkerService	handyWorkerService;
	
	@Autowired
	private ActorService		actorService;


	public Tutorial create() {
		final HandyWorker principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		final Tutorial t = new Tutorial();
		t.setHandyWorker(principal);
		final Collection<Section> s = new ArrayList<Section>();
		t.setSections(s);
		final Collection<Sponsorship> sp = new ArrayList<Sponsorship>();
		t.setSponsorships(sp);
		return t;
	}

	public Collection<Tutorial> findByHandyWorker() {
		final int id = this.handyWorkerService.findByPrincipal().getId();
		return this.tutorialRepository.findTutorial(id);
	}

	public Tutorial findOne(final int id) {
		return this.tutorialRepository.findById(id);
	}

	public Tutorial save(final Tutorial tutorial) {
		Assert.notNull(tutorial);
		Actor principal = this.actorService.findByPrincipal();
		final Date now = new Date();
		tutorial.setLastestUpdate(now);
		if(this.actorService.checkSpam(tutorial.getSummary())&& tutorial.getId()==0){
			principal.setIsSuspicious(true);
			this.actorService.save(principal);
		}
		if(this.actorService.checkSpam(tutorial.getTitle())&& tutorial.getId()==0){
			principal.setIsSuspicious(true);
			this.actorService.save(principal);
		}
		return this.tutorialRepository.save(tutorial);
	}

	public void delete(final Tutorial tutorial) {
		Assert.notNull(tutorial);
		
		this.tutorialRepository.delete(tutorial);
	}

	public Collection<Tutorial> findAll() {
		final Collection<Tutorial> result = new ArrayList<Tutorial>();
		result.addAll(this.tutorialRepository.findAll());
		return result;
	}
}
