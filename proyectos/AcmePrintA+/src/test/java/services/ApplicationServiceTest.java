
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
import domain.Customer;

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
	private CustomerService		customerService;


	/** List applications by customer **/
	// Requirement: 21.2. Manage his or her applications, which includes listing them grouped by status, showing them, creating them, and updating them.

	// 1. Listar applications siendo customer

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// 2. Listar applications de un customer siendo company

	// Analysis of sentence coverage total: Covered 3.4% 10/296 total instructions
	// Analysis of sentence coverage findApplicationsByCustomer(): Covered 100% 7/7 total instructions

	// Analysis of data coverage: 100%
	@Test
	public void listApplicationByCustomerTestDriver() {

		final Object testingData[][] = {

			// 1. TEST POSITIVO: Listar applications siendo customer
			{
				"customer1", null
			},

			// 2. TEST NEGATIVO: Listar applications de un customer siendo company
			{
				"company1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listApplicationCustomerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void listApplicationCustomerTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			final Customer customer = this.customerService.findByPrincipal();

			this.applicationService.findApplicationsByCustomerId(customer.getId());

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/** List applications by company **/
	// Requirement: 22.2. Manage his or her applications,
	// 1. Listar applications siendo company

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// 2. Listar applications de una company siendo customer

	// Analysis of sentence coverage total: Covered 3.4% 10/296 total instructions
	// Analysis of sentence coverage findApplicationsByCompany(): Covered 100% 7/7 total instructions

	// Analysis of data coverage: 100%
	@Test
	public void listApplicationByCompanyTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO: Listar applications siendo company
			{
				"company1", null
			},

			// TEST NEGATIVO: Un customer intenta listar applications de una company
			{
				"customer1", IllegalArgumentException.class
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

			this.applicationService.findApplicationsByCompanyId(company.getId());

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/** Accept application **/
	// Requirement: 21.2. Manage the applications for his or her orders,

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// 2. Un customer intenta manejar una application que no es suya
	// 3. Una company intenta aceptar una application 

	// Analysis of sentence coverage total: Covered 26.4% 78/296 total instructions
	// Analysis of sentence coverage accept(): Covered 86.2% 56/65 total instructions
	// Analysis of sentence coverage findApplicationsPendingsByOrderAndCustomer(): Covered 100% 8/8 total instructions

	// Analysis of data coverage: 25% 
	@Test
	public void acceptApplicationTestDriver() {

		final Object testingData[][] = {

			// 1. TEST POSITIVO: 
			{
				"customer1", super.getEntityId("application18"), null
			},

			// 2. TEST NEGATIVO: 
			{
				"customer1", super.getEntityId("application23"), IllegalArgumentException.class
			},

			// 3. TEST NEGATIVO: 
			{
				"company1", super.getEntityId("application24"), IllegalArgumentException.class
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
	// Requierement: 21.2. Manage the applications for his or her orders,
	// 1. Un customer rechaza una application con estado submitted

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// 2. Un customer intenta rechazar una application que no es suya
	// 3. Una company intenta rechazar una application

	// Analysis of sentence coverage total: Covered 19.3% 53/296 total instructions
	// Analysis of sentence coverage reject(): Covered 97.7% 43/44 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions

	// Analysis of data coverage: 25% 
	@Test
	public void rejectApplicationTestDriver() {

		final Object testingData[][] = {

			// 1. TEST POSITIVO: 
			{
				"customer1", super.getEntityId("application18"), null
			},

			// 2. TEST NEGATIVO: 
			{
				"customer1", super.getEntityId("application23"), IllegalArgumentException.class
			},

			// 3. TEST NEGATIVO: 			
			{
				"company1", super.getEntityId("application24"), IllegalArgumentException.class
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

	/** Create application **/
	// Requierement: 22.2. Manage his or her applications, 
	// 1. Un company intenta crear una solicitud

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// 2. Un customer intenta crear una solicitud

	// Analysis of sentence coverage total: Covered 22.8% 104/456 total instructions
	// Analysis of sentence coverage create(): Covered 100% 54/54 total instructions
	// Analysis of sentence coverage save(): Covered 67.1% 47/70 total instructions

	// Analysis of data coverage: 81.25%
	@Test
	public void createApplicationTestDriver() {

		final Object testingData[][] = {

			// 1. TEST POSITIVO: 
			{
				"company1", super.getEntityId("order4"), null
			},

			// 2. TEST NEGATIVO: 
			{
				"customer2", super.getEntityId("order4"), IllegalArgumentException.class
			}, {
				"provider1", super.getEntityId("order4"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.createApplicationTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void createApplicationTemplate(final String username, final int orderId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			final Application application = this.applicationService.create(orderId);

			this.applicationService.save(application);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
