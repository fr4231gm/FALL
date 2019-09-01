package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Administrator;
import domain.Company;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService adminService;

	@Autowired
	private CompanyService companyService;

	// Requirement: 16. An actor who is authenticated as a sponsor must be able
	// to:
	// 1. Manage his or her sponsorships which have credit cards

	// Requierement: 24.3 An actor who is authenticated as a administrator must be able
	// to:
	// Ban an actor with the spammer flag

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Ban an actor that have the spammer flag off
	
	// Analysis of sentence coverage total: Covered 8% 56/704 total instructions
	// Analysis of sentence coverage changeBan(): Covered 96.6% 26/27 total instructions
	// Analysis of sentence coverage findAdministratorByUserAccount(): Covered 93.3% 14/15 total instructions
	// Analysis of sentence coverage findByPrincipal(): Covered 100% 13/13 total instructions
	
	// Analysis of data coverage: 100%

	@Test
	public void changeBanTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				true, null
			}, // Editar configuracion siendo admin
				// TEST POSITIVO:
				{ true, null }, // Editar configuracion siendo admin

			// TEST NEGATIVO:
			{
				false, IllegalArgumentException.class
			}, 
			// TEST NEGATIVO:
			{ false, IllegalArgumentException.class }, 
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.changeBanTemplate((boolean) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	// Templates---------------------------------------------------------------------------------

	protected void changeBanTemplate(final boolean spammer,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate("admin1");
			final Company company = this.companyService.findOne(super
					.getEntityId("company5"));
			company.setIsSpammer(spammer);
			this.adminService.changeBan(company.getUserAccount());

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 8. An actor who is authenticated must be able to:
	// 2. Edit his or her personal data.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Editar los datos personales de un Administrator estando registrado como otro
	// actor.

	// Analysis of sentence coverage total: Covered 10.1% 71/704 total instructions
		// Analysis of sentence coverage save(): Covered 63.4% 26/41 total instructions
		// Analysis of sentence coverage findAdministratorByUserAccount(): Covered 93.3% 14/15 total instructions
		// Analysis of sentence coverage findByPrincipal(): Covered 100% 13/13 total instructions
		// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions
		// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
		
		// Analysis of data coverage: 57.4%

	@Test
	public void editAdministratorTestDriver() {
		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"name", "surname", "ES12345678L", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "admin1", super.getEntityId("administrator1"), null
			},

			// TEST NEGATIVO:
			{
				"name", "surname", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "admin1", super.getEntityId("hacker1"), IllegalArgumentException.class
			},

		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.editAdministratorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (int) testingData[i][8], (Class<?>) testingData[i][9]);
		this.rollbackTransaction();
	}

	protected void editAdministratorTemplate(final String name, final String surname, final String vatNumber, final String photo, final String phoneNumber, final String email, final String address, final String username, final int adminId,
		final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			super.authenticate(username);

			final Administrator administrator = this.adminService.findOne(adminId);
			administrator.setName(name);
			administrator.setSurname(surname);
			administrator.setVatNumber(vatNumber);
			administrator.setAddress(address);
			administrator.setEmail(email);
			administrator.setPhoto(photo);
			administrator.setPhoneNumber(phoneNumber);

			this.adminService.save(administrator);

			this.adminService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//C-RI-11.1. Administrator -> Create New Admin
	//An actor who is authenticated as an administrator must be able to:
	//Create user accounts for new administrators.
	
	//In the case of negative tests, the business rule that is intended to be broken:
	//Wrong data into the form.
	
	// Analysis of sentence coverage total: Covered 15.8% 111/704 total instructions
	// Analysis of sentence coverage save(): Covered 78% 32/41 total instructions
	// Analysis of sentence coverage findAdministratorByUserAccount(): Covered 93.3% 14/15 total instructions
	// Analysis of sentence coverage findByPrincipal(): Covered 100% 13/13 total instructions
	// Analysis of sentence coverage create(): Covered 100% 45/45 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
			
	// Analysis of data coverage: 55.5%

	
	// Driver
	@Test
	public void registerAdministratorTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ "name", "surname", "ES21145872H",
				"http://www.google.es/photo.jpg", "623116229",
				"email@gmail.com", "address", "admin6", "admin6", null }, // Registrarse
																			// correctamente

		// TEST NEGATIVO:
		{ "", "surname", "ES21145819H",
				"http://www.google.es/photo.jpg", "623116229",
				"email@gmail.com", "address", "admin8", "admin8",
				ConstraintViolationException.class },
		// Intentar registrarse con el name vacio

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.registerAdministratorTemplate(
					(String) testingData[i][0],
					(String) testingData[i][1], 
					(String) testingData[i][2],
					(String) testingData[i][3], 
					(String) testingData[i][4],
					(String) testingData[i][5], 
					(String) testingData[i][6],
					(String) testingData[i][7], 
					(String) testingData[i][8],
					(Class<?>) testingData[i][9]);
			this.rollbackTransaction();
		}
	}

	// Template
	protected void registerAdministratorTemplate(
			final String name,
			final String surname, 
			final String vatNumber, 
			final String photo,
			final String phoneNumber, 
			final String email, 
			final String address,
			 final String username,
			final String password, 
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			super.authenticate("admin");
			Administrator administrator = this.adminService.create(username,
					password);
			administrator.setName(name);
			administrator.setSurname(surname);
			administrator.setVatNumber(vatNumber);
			administrator.setAddress(address);
			administrator.setEmail(email);
			administrator.setPhoto(photo);
			administrator.setPhoneNumber(phoneNumber);
			administrator.setIsSpammer(false);

			this.adminService.save(administrator);

			this.adminService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
