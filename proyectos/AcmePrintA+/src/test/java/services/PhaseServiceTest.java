
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Application;
import domain.Company;
import domain.Phase;
import domain.WorkPlan;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PhaseServiceTest extends AbstractTest {

	@Autowired
	private PhaseService			phaseService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private WorkPlanService			workplanService;

	@Autowired
	private ApplicationService		applicationService;


	//Requirement 22.3:  An actor who is authenticated as a company must be able to:
	//When a customer accepts an application, then the corresponding company can create a work plan for the corresponding order. They can fully manage the work plan, which includes showing them, creating, and updating phases. When a company updates a phase, they cant change its name, only the comments.

	//In the case of negative tests, the business rule that is intended to be broken:
	//A designer is trying to create the phases

	// Analysis of sentence coverage total: Covered 11.9% 27/227 total instructions
	// Analysis of sentence coverage create(): Covered 100% 24/24 total instructions

	// Analysis of data coverage: 20%

	//create method in the service
	@Test
	public void phaseCreateTestDriver() {

		final Object testingData[][] = {

			//TEST NEGATIVO:
			{
				"designer1", IllegalArgumentException.class
			}, {
				"customer3", IllegalArgumentException.class
			},
			//TEST POSITIVO:
			{
				"company1", null
			},

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.phaseCreateTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	//Templates---------------------------------------------------------------------------------

	protected void phaseCreateTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			Application a3 = new Application();
			final Company n = this.companyService.findByPrincipal();
			final Collection<Application> a = this.applicationService.findApplicationsByCompanyId(n.getId());
			for (final Application a2 : a)
				if (a2.getStatus().equals("ACCEPTED"))
					a3 = a2;

			final WorkPlan w = this.workplanService.create(a3.getId());

			@SuppressWarnings("unused")
			final Phase p = this.phaseService.create(w);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//In the case of negative tests, the business rule that is intended to be broken:
	//A designer is trying to save the phases

	// Analysis of sentence coverage total: Covered 23.3% 53/227 total instructions
	// Analysis of sentence coverage create(): Covered 100% 24/24 total instructions
	// Analylsis of sentence coverage save(): Covered 22% 26/118 total instructions
	// Analysis of data coverage: 20%

	//save method in the service

	@Test
	public void phaseSaveTestDriver() {

		final Object testingData[][] = {

			//TEST NEGATIVO:
			{
				"designer1", IllegalArgumentException.class
			}, {
				"provider1", IllegalArgumentException.class
			},

			//TEST POSITIVO:
			{
				"company1", null
			},

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.phaseSaveTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	//Templates---------------------------------------------------------------------------------

	protected void phaseSaveTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			Application a3 = new Application();
			final Company n = this.companyService.findByPrincipal();
			final Collection<Application> a = this.applicationService.findApplicationsByCompanyId(n.getId());
			for (final Application a2 : a)
				if (a2.getStatus().equals("ACCEPTED"))
					a3 = a2;

			final WorkPlan w = this.workplanService.create(a3.getId());
			final Phase p = this.phaseService.create(w);
			this.phaseService.save(p);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//Requirement 22.3:  An actor who is authenticated as a company must be able to:
	//When a customer accepts an application, then the corresponding company can create a work plan for the corresponding order. They can fully manage the work plan, which includes showing them, creating, and updating phases. When a company updates a phase, they cant change its name, only the comments.

	//In the case of negative tests, the business rule that is intended to be broken:
	//A designer is trying to create the phases

	// Analysis of sentence coverage total: Covered 45.8% 104/227 total instructions
	// Analysis of sentence coverage save(): Covered 22% 26/118 total instructions
	// Analysis of sentence coverage create(): Covered 100% 24/24 total instructions
	// Analysis of sentence coverage createPhases(): Covered 1100% 51/51 total instructions

	// Analysis of data coverage: 100%

	//Service method for create the standard phases
	@Test
	public void createPhasesTestDriver() {

		final Object testingData[][] = {

			//TEST NEGATIVO:
			{
				"designer1", IllegalArgumentException.class
			}, {
				"customer4", IllegalArgumentException.class
			},

			//TEST POSITIVO:
			{
				"company1", null
			},

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.createPhasesTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	//Templates---------------------------------------------------------------------------------

	protected void createPhasesTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			Application a3 = new Application();
			final Company n = this.companyService.findByPrincipal();
			final Collection<Application> a = this.applicationService.findApplicationsByCompanyId(n.getId());
			for (final Application a2 : a)
				if (a2.getStatus().equals("ACCEPTED"))
					a3 = a2;

			final WorkPlan w = this.workplanService.create(a3.getId());
			final String[] phasesName = this.configurationService.findConfiguration().getPhaseNames().split(",");
			final Collection<String> phasesNames = new ArrayList<>(Arrays.asList(phasesName));

			this.phaseService.createPhases(w, phasesNames);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
