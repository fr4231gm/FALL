
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.Actor;
import domain.Application;
import domain.HandyWorker;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	@Autowired
	private PhaseRepository		phaseRepository;

	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private ActorService		actorService;


	public Phase create() {
		Phase result;
		result = new Phase();
		return result;
	}

	public Collection<Phase> findPhasesByApplication(final int id) {
		HandyWorker principal;
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		Application a;
		a = this.applicationService.findById(id);
		Assert.notNull(a);
		final Collection<Phase> result = new ArrayList<Phase>();
		Assert.isTrue(principal.getApplications().contains(a));
		result.addAll(this.phaseRepository.findByApplication(id));
		return result;
	}

	public Phase save(final Phase phase) {
		Phase result;
		Assert.isTrue(phase.getApplication().getStatus().equals("ACCEPTED"));
		Assert.notNull(phase);
		final Actor principal = this.handyWorkerService.findByPrincipal();
		if (this.actorService.checkSpam(phase.getDescription())) {
			principal.setIsSuspicious(true);
			this.actorService.save(principal);
		}
		if (this.actorService.checkSpam(phase.getTitle())) {
			principal.setIsSuspicious(true);
			this.actorService.save(principal);
		}
		final Date startPhase = phase.getStart();
		final Date now = new Date();
		Assert.isTrue(startPhase.after(now));
		final Date endPhase = phase.getEnd();
		Assert.isTrue(startPhase.before(endPhase));
		final Date startTask = phase.getApplication().getFixUpTask().getStartDate();
		final Date endTask = phase.getApplication().getFixUpTask().getEndDate();
		Assert.isTrue(startPhase.after(startTask) && endPhase.before(endTask));
		result = this.phaseRepository.save(phase);
		Assert.notNull(result);
		return result;
	}

	public void delete(final Phase phase) {
		Assert.notNull(phase);
		Assert.isTrue(phase.getId() != 0);
		this.phaseRepository.delete(phase.getId());
	}

	public Collection<Phase> findAll() {
		final Collection<Phase> result = new ArrayList<Phase>();
		result.addAll(this.phaseRepository.findAll());
		return result;
	}

	public Phase findOnde(final int id) {
		return this.phaseRepository.findOne(id);
	}

}
