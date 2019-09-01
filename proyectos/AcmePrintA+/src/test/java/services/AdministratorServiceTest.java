
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

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;


	// Requirement: RF 24. An actor who is authenticated as an administrator must be able to:
	// 1. Create user accounts for new administrators.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Intentar registrarse con el name vacio.
	// Intentar registrarse con el VAT Number vacio.

	// Analysis of sentence coverage total: Covered 11.0% 119/1079 total instructions
	// Analysis of sentence coverage save(): Covered 81.6% 40/49 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 45/45 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 13/13 total instructions

	// Analysis of data coverage: 59.09%

	@Test
	public void registerAdministratorTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"admin", "name", "surname", "A12345678", "middleName", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "adminTest", "adminTest", null
			},

			// TEST NEGATIVO:
			{
				"admin", "", "surname", "A12345678", "middleName", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "adminTest", "adminTest", ConstraintViolationException.class
			},
			
			// TEST NEGATIVO:
			{
				"admin", "name", "surname", "", "middleName", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "adminTest", "adminTest", ConstraintViolationException.class
			},

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.registerAdministratorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (Class<?>) testingData[i][11]);
			this.rollbackTransaction();
		}
	}

	// Template
	protected void registerAdministratorTemplate(final String admin2, final String name, final String surname, final String vatNumber, final String middleName, final String photo, final String phoneNumber, final String email, final String address,
		final String username, final String password, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			super.authenticate(admin2);
			final Administrator admin = this.administratorService.create(username, password);

			admin.setName(name);
			admin.setSurname(surname);
			admin.setMiddleName(middleName);
			admin.setVatNumber(vatNumber);
			admin.setAddress(address);
			admin.setEmail(email);
			admin.setPhoto(photo);
			admin.setPhoneNumber(phoneNumber);
			admin.setIsSpammer(false);

			this.administratorService.save(admin);

			this.administratorService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 19. An actor who is authenticated must be able to:
	// 2. Edit his or her personal data.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Editar los datos personales de un Administrator estando registrado como otro
	// actor.
	// Editar campo name y dejarlo vacio.

	// Analysis of sentence coverage total: Covered 6.9% 74/1079 total instructions
	// Analysis of sentence coverage save(): Covered 59.2% 29/49 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 54.0%

	@Test
	public void editAdministratorTestDriver() {
		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"name", "surname", "middleName", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "admin", super.getEntityId("administrator0"), null
			},

			// TEST NEGATIVO:
			{
				"name", "surname", "middleName", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "company1", super.getEntityId("administrator0"), IllegalArgumentException.class
			},
			
			// TEST NEGATIVO:
			{
				"", "surname", "middleName", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "admin", super.getEntityId("administrator0"), ConstraintViolationException.class
			},

		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.editAdministratorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (int) testingData[i][9], (Class<?>) testingData[i][10]);
		this.rollbackTransaction();
	}

	protected void editAdministratorTemplate(final String name, final String surname, final String middleName, final String vatNumber, final String photo, final String phoneNumber, final String email, final String address, final String username,
		final int administratorId, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			super.authenticate(username);

			final Administrator administrator = this.administratorService.findOne(administratorId);

			administrator.setName(name);
			administrator.setSurname(surname);
			administrator.setMiddleName(middleName);
			administrator.setVatNumber(vatNumber);
			administrator.setAddress(address);
			administrator.setEmail(email);
			administrator.setPhoto(photo);
			administrator.setPhoneNumber(phoneNumber);

			this.administratorService.save(administrator);

			this.administratorService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}


	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private CompanyService			companyService;


	// Requierement: 24.5 An actor who is authenticated as a administrator must be able
	// to:
	// Ban an actor with the spammer flag

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Ban an actor that have the spammer flag off

	// Analysis of sentence coverage total: Covered 11% 55/498 total instructions
	// Analysis of sentence coverage changeBan(): Covered 92.6% 25/27 total instructions
	// Analysis of sentence coverage findAdministratorByUserAccount(): Covered 93.3% 14/15 total instructions
	// Analysis of sentence coverage findByPrincipal(): Covered 100% 13/13 total instructions

	// Analysis of data coverage: 100%

	@Test
	public void changeBanTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				true, null
			},
			// TEST POSITIVO:
			{
				true, null
			},

			// TEST NEGATIVO:
			{
				false, IllegalArgumentException.class
			},
			// TEST NEGATIVO:
			{
				false, IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.changeBanTemplate((boolean) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	// Templates---------------------------------------------------------------------------------

	protected void changeBanTemplate(final boolean spammer, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate("admin1");
			final Company company = this.companyService.findOne(super.getEntityId("company5"));
			company.setIsSpammer(spammer);
			this.adminService.changeBan(company.getUserAccount());

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requirement: 24.6 An actor who is authenticated as a administrator must be able to:
	// Launch a process to compute an audit score for every designer.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Intantar lanzar el calculo estando logeado como provider.

	// Analysis of sentence coverage total: Covered 5.6% 28/498 total instructions
	// Analysis of sentence coverage score(): Covered 47.2% 25/53 total instructions

	// Analysis of data coverage: 20.0%

	@Test
	public void scoreTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"admin1", "designer1", null
			},

			// TEST NEGATIVO:
			{
				"provider1", "designer1", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.scoreTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	// Templates---------------------------------------------------------------------------------

	protected void scoreTemplate(final String username, final String designerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.adminService.score(this.getEntityId(designerId));

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
