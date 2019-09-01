package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Company;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CompanyServiceTest extends AbstractTest {

	@Autowired
	private CompanyService companyService;

	// Requirement: RF 18. An actor who is not authenticated must be able to:
	// 1. Register to the system as a company.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Intentar registrarse con el name vacio.
	// Intentar registrarse con el VAT Number vacio.

	// Analysis of sentence coverage total: Covered 20.0% 94/469 total instructions
	// Analysis of sentence coverage save(): Covered 77.0% 47/61 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 40/40 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 59.09%

	@Test
	public void registerCompanyTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "name", "middleName", "surname", "commercialName",
						"A12345678", "http://www.google.es/photo.jpg",
						"123456789", "email@gmail.com", "address",
						"companyTest", "companyTest", null },

				// TEST NEGATIVO:
				{ "", "middleName", "surname", "commercialName", "A12345678",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "companyTest",
						"companyTest", ConstraintViolationException.class },
				
				// TEST NEGATIVO:
				{ "name", "middleName", "surname", "commercialName", "",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "companyTest",
						"companyTest", ConstraintViolationException.class },	

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.registerCompanyTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8],
					(String) testingData[i][9], (String) testingData[i][10],
					(Class<?>) testingData[i][11]);
			this.rollbackTransaction();
		}
	}

	protected void registerCompanyTemplate(final String name,
			final String middleName, final String surname,
			final String commercialName, final String vatNumber,
			final String photo, final String phoneNumber, final String email,
			final String address, final String username, final String password,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			final Company company = this.companyService.create(username,
					password);

			company.setName(name);
			company.setMiddleName(middleName);
			company.setSurname(surname);
			company.setCommercialName(commercialName);
			company.setVatNumber(vatNumber);
			company.setAddress(address);
			company.setEmail(email);
			company.setPhoto(photo);
			company.setPhoneNumber(phoneNumber);
			company.setIsSpammer(false);

			this.companyService.save(company);

			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requirement: RF 19. An actor who is authenticated must be able to:
	// 2. Edit his or her personal data.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Editar los datos personales de una Company estando registrado como otro
	// actor.
	// Editar campo name y dejarlo vacio.

	// Analysis of sentence coverage total: Covered 15.8% 74/469 total instructions
	// Analysis of sentence coverage save(): Covered 47.5% 29/61 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 54.0%

	@Test
	public void editCompanyTestDriver() {
		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "name", "commercialName", "surname", "middleName",
						"A12345678", "http://www.google.es/photo.jpg",
						"123456789", "email@gmail.com", "address", "company1",
						super.getEntityId("company1"), null },

				// TEST NEGATIVO:
				{ "name", "commercialName", "surname", "middleName",
						"A12345678", "http://www.google.es/photo.jpg",
						"123456789", "email@gmail.com", "address", "designer1",
						super.getEntityId("company1"),
						IllegalArgumentException.class },
						
				// TEST NEGATIVO:
				{ "", "commercialName", "surname", "middleName",
						"A12345678", "http://www.google.es/photo.jpg",
						"123456789", "email@gmail.com", "address", "company1",
						super.getEntityId("company1"),
						ConstraintViolationException.class },		

		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.editCompanyTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8],
					(String) testingData[i][9], (int) testingData[i][10],
					(Class<?>) testingData[i][11]);
		this.rollbackTransaction();
	}

	protected void editCompanyTemplate(final String name,
			final String commercialName, final String surname,
			final String middleName, final String vatNumber,
			final String photo, final String phoneNumber, final String email,
			final String address, final String username, final int companyId,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			super.authenticate(username);

			Company company = this.companyService.findOne(companyId);

			company.setName(name);
			company.setCommercialName(commercialName);
			company.setSurname(surname);
			company.setMiddleName(middleName);
			company.setVatNumber(vatNumber);
			company.setAddress(address);
			company.setEmail(email);
			company.setPhoto(photo);
			company.setPhoneNumber(phoneNumber);

			this.companyService.save(company);

			this.companyService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requirement: 18.3 An actor who is not authenticated must be able to:
	// List the companies available to attend orders and navigate to their profile.

	// In the case of negative tests, the business rule that is intended to be broken:
	// No procede

	// Analysis of sentence coverage total: Covered 8.5% 39/461 total instructions
	// Analysis of sentence coverage findCompaniesAvailableToAttendOrders(): Covered 100.0% 32/32 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 100.0%

	@Test
	public void listCompaniesTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ null } };

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listCompaniesTemplate((Class<?>) testingData[i][0]);
			this.rollbackTransaction();
		}
	}

	protected void listCompaniesTemplate(final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			this.companyService.findCompaniesAvailableToAttendOrders();

			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requirement: 18.3 An actor who is not authenticated must be able to:
	// List the companies available to attend orders and navigate to their profile.

	// In the case of negative tests, the business rule that is intended to be broken:
	// No procede

	// Analysis of sentence coverage total: Covered 3.9% 18/461 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 20.0%

	@Test
	public void displayCompanyTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ "company1", null } };

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.displayCompanyTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void displayCompanyTemplate(final String companyId,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			this.companyService.findOne(this.getEntityId(companyId));

			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
