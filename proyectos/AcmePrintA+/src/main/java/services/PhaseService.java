
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.Application;
import domain.Company;
import domain.Order;
import domain.Phase;
import domain.WorkPlan;

@Service
@Transactional
public class PhaseService {

	// Managed repository ------------------------------------------------------
	@Autowired
	private PhaseRepository	phaseRepository;

	// Supporting services -----------------------------------------------------

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private OrderService	orderService;

	@Autowired
	private WorkPlanService	workplanService;


	// Simple CRUD methods -----------------------------------------------------

	public Phase create(final WorkPlan w) {
		final Company principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(w);
		Assert.isTrue(w.getApplication().getStatus().equals("ACCEPTED"));

		final Phase res = new Phase();
		res.setIsDone(false);

		return res;
	}

	public Phase save(final Phase p) {
		Phase res;
		Assert.notNull(p);

		final Company principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);
		if (this.phaseRepository.findApplication(p.getId()) != null)
			if (this.workplanService.findByApplicationId(this.findApplication(p).getId()).getPhases() != null) {
				final WorkPlan w = this.workplanService.findByApplicationId(this.findApplication(p).getId());
				final Collection<Phase> phases = this.workplanService.findByApplicationId(this.findApplication(p).getId()).getPhases();
				this.phaseRepository.findApplication(p.getId()).getOrder().setStatus(p.getName().toUpperCase());
				for (final Phase p2 : phases)
					if (p2.getNumber() == p.getNumber() + 1)
						if (p.getIsDone() == true) {
							phases.remove(p2);
							p2.setIsDoneable(true);
							phases.add(p2);
							w.setPhases(phases);
							this.workplanService.save(w);
							this.phaseRepository.save(p2);
							break;
						}
			}

		res = this.phaseRepository.save(p);

		if (res.getIsDone()) {
			this.flush();

			final Application ap = this.findApplication(res);
			final Order order = ap.getOrder();

			this.orderService.updateOrderStatus(order.getId(), res.getName());
		}

		return res;
	}
	// FIND ONE
	public Phase findOne(final int phaseId) {

		// Declaracion de variables
		Phase res;

		// Obtenemos la Phase con la id indicada y comprobamos
		// que existe una Phase con esa id
		res = this.phaseRepository.findOne(phaseId);
		Assert.notNull(res);

		return res;
	}

	// FIND ALL
	public Collection<Phase> findAll() {

		// Declaracion de variables
		Collection<Phase> res;

		// Obtenemos el conjunto de Phase
		res = this.phaseRepository.findAll();

		return res;
	}

	public Collection<Phase> createPhases(final WorkPlan workplan, final Collection<String> phasesNames) {

		Assert.isTrue(this.actorService.findByPrincipal() instanceof Company);
		final Collection<Phase> phases = new ArrayList<Phase>();
		int x = 1;
		for (final String s : phasesNames) {
			final Phase p = this.create(workplan);
			if (x == 1)
				p.setIsDoneable(true);
			p.setName(s);
			p.setNumber(x);
			this.save(p);
			phases.add(p);

			x++;
		}

		return phases;
	}

	public Application findApplication(final Phase p) {
		final Application res = this.phaseRepository.findApplication(p.getId());
		Assert.notNull(res);
		return res;
	}

	public void flush() {
		this.phaseRepository.flush();
	}

}
