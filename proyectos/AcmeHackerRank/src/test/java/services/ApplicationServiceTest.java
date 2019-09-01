
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Application;
import domain.Company;
import domain.Hacker;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// Service under test ---------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private HackerService		hackerService;


	/** List applications by hacker **/
	// Requierement: Manage his or her applications, which includes listing them grouped by status, showing them, creating them, and updating them.

	// 1. Listar applications siendo hacker

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// 2. Listar applications de un hacker siendo company

	// Analysis of sentence coverage total: Covered 2.2% 10/456 total instructions
	// Analysis of sentence coverage listApplicationsByHacker(): Covered 100% 7/7 total instructions

	// Analysis of data coverage: 100%
	@Test
	public void listApplicationByHackerTestDriver() {

		final Object testingData[][] = {

			// 1. TEST POSITIVO: Listar applications siendo hacker
			{
				"hacker1", null
			},

			// 2. TEST NEGATIVO: Listar applications de un hacker siendo company
			{
				"company1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listApplicationHackerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void listApplicationHackerTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			final Hacker hacker = this.hackerService.findByPrincipal();

			this.applicationService.findApplicationsByHacker(hacker.getId());

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/** List applications by company **/
	// Requierement: Manage the applications to their positions, which includes listing them grouped by status, showing them, and updating them. Updating an application amounts to making a decision on it: an application whose status is SUBMITTED may change to status ACCEPTED or REJECTED.

	// 1. Listar applications siendo company

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// 2. Listar applications de una company siendo hacker

	// Analysis of sentence coverage total: Covered 2.2% 10/456 total instructions
	// Analysis of sentence coverage listApplicationsByCompany(): Covered 100% 7/7 total instructions

	// Analysis of data coverage: 100%
	@Test
	public void listApplicationByCompanyTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO: Listar applications siendo company
			{
				"company1", null
			},

			// TEST NEGATIVO: Un hacker intenta listar applications de una company
			{
				"hacker1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listApplicationCompanyTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void listApplicationCompanyTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			final Company company = this.companyService.findByPrincipal();

			this.applicationService.findApplicationsByCompany(company.getId());

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/** Accept application **/
	// Requierement: Manage the applications to their positions, which includes listing them grouped by status, showing them, and updating them. Updating an application amounts to making a decision on it: an application whose status is SUBMITTED may change to status ACCEPTED or REJECTED.

	// 1. Una company acepta una application con estado submitted

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// 2. Un hacker intenta aceptar una application con estado submitted
	// 3. Una company intenta aceptar una application con estado distinto de submitted

	// Analysis of sentence coverage total: Covered 9.6% 44/456 total instructions
	// Analysis of sentence coverage accept(): Covered 96.8% 30/31 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions

	// Analysis of data coverage: 25% 
	@Test
	public void acceptApplicationTestDriver() {

		final Object testingData[][] = {

			// 1. TEST POSITIVO: 
			{
				"company1", super.getEntityId("application3"), null
			},

			// 2. TEST NEGATIVO: 
			{
				"hacker1", super.getEntityId("application3"), IllegalArgumentException.class
			},

			// 3. TEST NEGATIVO: 
			{
				"company1", super.getEntityId("application1"), IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.acceptApplicationTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void acceptApplicationTemplate(final String username, final int applicationId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			final Application application = this.applicationService.findOne(applicationId);

			this.applicationService.accept(application);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/** Reject application **/
	// Requierement: Manage the applications to their positions, which includes listing them grouped by status, showing them, and updating them. Updating an application amounts to making a decision on it: an application whose status is SUBMITTED may change to status ACCEPTED or REJECTED.
	// 1. Una company rechaza una application con estado submitted

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// 2. Un hacker intenta rechazar una application con estado submitted
	// 3. Una company intenta rechazar una application con estado distinto de submitted

	// Analysis of sentence coverage total: Covered 9.6% 44/456 total instructions
	// Analysis of sentence coverage reject(): Covered 96.8% 30/31 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions

	// Analysis of data coverage: 25% 
	@Test
	public void rejectApplicationTestDriver() {

		final Object testingData[][] = {

			// 1. TEST POSITIVO: 
			{
				"company1", super.getEntityId("application3"), null
			},

			// 2. TEST NEGATIVO: 
			{
				"hacker1", super.getEntityId("application3"), IllegalArgumentException.class
			},

			// 3. TEST NEGATIVO: 
			{
				"company1", super.getEntityId("application1"), IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.rejectApplicationTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void rejectApplicationTemplate(final String username, final int applicationId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			final Application application = this.applicationService.findOne(applicationId);

			this.applicationService.reject(application);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/** Submit application **/
	// Requierement: Manage the applications to their positions, which includes listing them grouped by status, showing them, and updating them. Updating an application amounts to making a decision on it: an application whose status is SUBMITTED may change to status ACCEPTED or REJECTED.
	// 1. Un hacker envía una application con estado pending

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// 2. Una company intenta enviar una application con estado pending
	// 3. Un hacker envía una application con estado distinto de submitted

	// Analysis of sentence coverage total: Covered 15.6% 71/456 total instructions
	// Analysis of sentence coverage submit(): Covered 90.5% 57/63 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions

	// Analysis of data coverage: 25%
	@Test
	public void submitApplicationTestDriver() {

		final Object testingData[][] = {

			// 1. TEST POSITIVO:
			{
				"hacker8", super.getEntityId("application6"), null
			},

			// 2. TEST NEGATIVO:
			{
				"company1", super.getEntityId("application6"), IllegalArgumentException.class
			},

			// 3. TEST NEGATIVO: 
			{
				"company1", super.getEntityId("application1"), IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.submitApplicationTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void submitApplicationTemplate(final String username, final int applicationId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			final Application application = this.applicationService.findOne(applicationId);

			this.applicationService.submit(application);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/** Create application **/
	// Requierement: Manage his or her applications, which includes listing them grouped by status, 
	// showing them, creating them, and updating them. When an application is created, the system assigns an arbitrary problem to it (from the set of problems that have been registered for the corresponding position). Updating an application consists in sub- mitting a solution to the corresponding problem (a piece of text with explanations and a link to the code), registering the submission moment, and changing the status to SUBMITTED.
	// 1. Un hacker intenta crear una solicitud

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// 2. Una company intenta crear una solicitud

	// Analysis of sentence coverage total: Covered 22.8% 104/456 total instructions
	// Analysis of sentence coverage create(): Covered 100% 54/54 total instructions
	// Analysis of sentence coverage save(): Covered 67.1% 47/70 total instructions

	// Analysis of data coverage: 81.25%
	@Test
	public void createApplicationTestDriver() {

		final Object testingData[][] = {

			// 1. TEST POSITIVO: 
			{
				"hacker1", super.getEntityId("position1"), null
			},

			// 2. TEST NEGATIVO: 
			{
				"company1", super.getEntityId("position2"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.createApplicationTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void createApplicationTemplate(final String username, final int positionId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			final Application application = this.applicationService.create(positionId);

			this.applicationService.save(application);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/** Edit application **/
	// Requierement: Manage his or her applications, which includes listing them grouped by status, 
	// showing them, creating them, and updating them. When an application is created, the system assigns an arbitrary problem to it (from the set of problems that have been registered for the corresponding position). Updating an application consists in sub- mitting a solution to the corresponding problem (a piece of text with explanations and a link to the code), registering the submission moment, and changing the status to SUBMITTED.
	// 1. Un hacker edita una solicitud suya con estado pending

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// 2. Un hacker intenta editar una solicitud suya con estado no pending
	// 3. Un hacker intenta editar una solicitud que no es suya con estado pending

	// Analysis of sentence coverage total: Covered 13.8% 104/456 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions
	// Analysis of sentence coverage save(): Covered 64.3% 45/70 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions

	// Analysis of data coverage: 58.33%
	@Test
	public void editApplicationTestDriver() {

		final Object testingData[][] = {

			// 1. TEST POSITIVO:

			{
				"hacker8", super.getEntityId("application6"), null
			},

			// 2. TEST NEGATIVO: 

			{
				"hacker7", super.getEntityId("application5"), IllegalArgumentException.class
			},

			// 3. TEST NEGATIVO: 
			{
				"hacker7", super.getEntityId("application6"), IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.editApplicationTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void editApplicationTemplate(final String username, final int applicationId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			final Application application = this.applicationService.findOne(applicationId);

			this.applicationService.save(application);
			this.applicationService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
