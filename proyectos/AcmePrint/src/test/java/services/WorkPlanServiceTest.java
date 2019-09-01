
package services;

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
import domain.WorkPlan;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class WorkPlanServiceTest extends AbstractTest {

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private WorkPlanService		workplanService;

	@Autowired
	private ApplicationService	applicationService;


	//Requirement 22.3:  An actor who is authenticated as a company must be able to:
	//When a customer accepts an application, then the corresponding company can create a work plan for the corresponding order. They can fully manage the work plan, which includes showing them, creating, and updating phases. When a company updates a phase, they cant change its name, only the comments.

	//In the case of negative tests, the business rule that is intended to be broken:
	//A designer is trying to create the workplan

	// Analysis of sentence coverage total: Covered 32.8% 38/116 total instructions
	// Analysis of sentence coverage create(): Covered 100% 35/35 total instructions

	// Analysis of data coverage: 100%

	@Test
	public void workPlanCreateTestDriver() {

		final Object testingData[][] = {

			//TEST NEGATIVO:
			{
				"designer1", IllegalArgumentException.class
			}, {
				"admin1", IllegalArgumentException.class
			},

			//TEST POSITIVO:
			{
				"company1", null
			},

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.workplanCreateTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	//Templates---------------------------------------------------------------------------------

	protected void workplanCreateTemplate(final String username, final Class<?> expected) {
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

			@SuppressWarnings("unused")
			final WorkPlan w = this.workplanService.create(a3.getId());

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//Requirement 22.3:  An actor who is authenticated as a company must be able to:
	//When a customer accepts an application, then the corresponding company can create a work plan for the corresponding order. They can fully manage the work plan, which includes showing them, creating, and updating phases. When a company updates a phase, they cant change its name, only the comments.

	//In the case of negative tests, the business rule that is intended to be broken:
	//A designer is logged while the sistem is trying to save the workplan

	// Analysis of sentence coverage total: Covered 51.7% 60/116 total instructions
	// Analysis of sentence coverage create(): Covered 100% 35/35 total instructions
	// Analysis of sentence coverage save(): Covered 100% 22/22 instructions

	// Analysis of data coverage: 100%

	@Test
	public void workPlanSaveTestDriver() {

		final Object testingData[][] = {

			//TEST NEGATIVO:
			{
				"designer1", IllegalArgumentException.class
			}, {
				"admin1", IllegalArgumentException.class
			},

			//TEST POSITIVO:
			{
				"company1", null
			},

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.workplanSaveTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	//Templates---------------------------------------------------------------------------------

	protected void workplanSaveTemplate(final String username, final Class<?> expected) {
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
			this.workplanService.save(w);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
