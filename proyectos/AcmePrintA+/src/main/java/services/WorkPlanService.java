
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WorkPlanRepository;
import utilities.Tools;
import domain.Application;
import domain.Company;
import domain.Phase;
import domain.WorkPlan;

@Service
@Transactional
public class WorkPlanService {

	// Managed repository ------------------------------------------------------
	@Autowired
	private WorkPlanRepository		workPlanRepository;

	// Supporting services -----------------------------------------------------

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private PhaseService			phaseService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ApplicationService		applicationService;


	// Simple CRUD methods -----------------------------------------------------

	public WorkPlan create(final int applicationId) {
		final Company principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);
		final Application application = this.applicationService.findOne(applicationId);

		Assert.notNull(application);
		Assert.isTrue(application.getStatus().equals("ACCEPTED"));
		final WorkPlan res = new WorkPlan();

		res.setTicker(Tools.generateTicker());
		res.setApplication(application);
		res.setPhases(new ArrayList<Phase>());

		return res;
	}
	public WorkPlan save(final WorkPlan w) {
		WorkPlan res;

		final Company principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(w);
		Assert.isTrue(w.getApplication().getStatus().equals("ACCEPTED"));

		res = this.workPlanRepository.save(w);

		return res;
	}

	// FIND ONE
	public WorkPlan findOne(final int workPlanId) {

		// Declaracion de variables
		WorkPlan res;

		// Obtenemos la WorkPlan con la id indicada y comprobamos
		// que existe una WorkPlan con esa id
		res = this.workPlanRepository.findOne(workPlanId);
		Assert.notNull(res);

		return res;
	}

	// FIND ALL
	public Collection<WorkPlan> findAll() {

		// Declaracion de variables
		Collection<WorkPlan> res;

		// Obtenemos el conjunto de WorkPlan
		res = this.workPlanRepository.findAll();

		return res;
	}

	public WorkPlan findByApplicationId(final int applicationId) {
		WorkPlan res;
		res = this.workPlanRepository.findByApplicationId(applicationId);

		return res;
	}
	public void createPhases(final WorkPlan workplan) {
		Assert.isTrue(this.actorService.findByPrincipal() instanceof Company);
		final String[] phasesName = this.configurationService.findConfiguration().getPhaseNames().split(",");
		//List<String> creditcardMakes = new ArrayList<>(Arrays.asList(makes));
		final Collection<String> phasesNames = new ArrayList<>(Arrays.asList(phasesName));
		final Collection<Phase> phases = this.phaseService.createPhases(workplan, phasesNames);
		workplan.setPhases(phases);
		this.save(workplan);

	}
}
