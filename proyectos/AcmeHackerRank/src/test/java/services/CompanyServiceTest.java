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

	// Requierement: RF 7. An actor who is not authenticated must be able to:
	// 1. Register to the system as a company.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Intentar registrarse con el name vacio.

	// Analysis of sentence coverage total: Covered 16.4% 74/451 total instructions
	// Analysis of sentence coverage save(): Covered 65.9% 27/41 total instructions
	// Analysis of sentence coverage create(): Covered 100% 40/40 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
		
	// Analysis of data coverage: 53.3%

	@Test
	public void registerCompanyTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ "name", "surname", "A12345678",
				"http://www.google.es/photo.jpg", "123456789",
				"email@gmail.com", "address", "MERCADONA", "Company8",
				"Company8", null }, // Registrarse correctamente

		// TEST NEGATIVO:
		{ "", "surname", "A12345678", "http://www.google.es/photo.jpg",
				"123456789", "email@gmail.com", "address", "MERCADONA",
				"Company8", "Company8",
				ConstraintViolationException.class },
		// Intentar registrarse con el name vacio

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.registerCompanyTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8],
					(String) testingData[i][9], (Class<?>) testingData[i][10]);
			this.rollbackTransaction();
		}
	}

	protected void registerCompanyTemplate(final String name,
			final String surname, final String vatNumber, final String photo,
			final String phoneNumber, final String email, final String address,
			final String commercialName, final String username,
			final String password, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			Company company = this.companyService.create(username, password);
			company.setName(name);
			company.setSurname(surname);
			company.setVatNumber(vatNumber);
			company.setAddress(address);
			company.setEmail(email);
			company.setPhoto(photo);
			company.setPhoneNumber(phoneNumber);
			company.setCommercialName(commercialName);

			this.companyService.save(company);

			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 7. An actor who is not authenticated must be able to:
	// 2. List the positions available and navigate to the corresponding
	// companies.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// No procede.

	// Analysis of sentence coverage total: Covered 2.9% 13/451 total instructions
	// Analysis of sentence coverage findAll(): Covered 100% 6/6 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
			
		// Analysis of data coverage: 100%

	@Test
	public void listCompaniesTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ null } // Listar las positions
		};

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

			this.companyService.findAll();

			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 8. An actor who is authenticated must be able to:
	// 1. Do the same as an actor who is not authenticated, but register to the
	// system.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// No procede.

	// Analysis of sentence coverage total: Covered 2.9% 13/451 total instructions
	// Analysis of sentence coverage findAll(): Covered 100% 6/6 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
			
	// Analysis of data coverage:100%

	@Test
	public void listCompaniesByActorTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "company1", null }, // Listar las positions

				// TEST POSITIVO:
				{ "hacker1", null }, // Listar las positions

				// TEST POSITIVO:
				{ "admin", null } // Listar las positions
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listCompaniesByActorTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void listCompaniesByActorTemplate(final String username,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			super.authenticate(username);

			this.companyService.findAll();

			this.companyService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 8. An actor who is authenticated must be able to:
	// 2. Edit his or her personal data.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Editar los datos personales de una Company estando registrado como otro
	// actor.

	// Analysis of sentence coverage total: Covered 15.7% 71/451 total instructions
	// Analysis of sentence coverage save(): Covered 63.4% 26/41 total instructions
	// Analysis of sentence coverage findCompanyByUserAccount(): Covered 93.9% 14/15 total instructions
	// Analysis of sentence coverage findByPrincipal(): Covered 100% 13/13 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
			
	// Analysis of data coverage: 48.33%

	@Test
	public void editCompanyTestDriver() {
		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "name", "surname", "A12345678",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "MERCADONA", "company1",
						super.getEntityId("company1"),null }, 

				// TEST NEGATIVO:
				{ "name", "surname", "A12345678", "http://www.google.es/photo.jpg",
						"123456789", "email@gmail.com", "address", "MERCADONA",
						"hacker1", super.getEntityId("company1"),
						IllegalArgumentException.class },
			

		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.editCompanyTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8],
					(int) testingData[i][9],(Class<?>) testingData[i][10]);
		this.rollbackTransaction();
	}

	protected void editCompanyTemplate(final String name,
			final String surname, final String vatNumber, final String photo,
			final String phoneNumber, final String email, final String address,
			final String commercialName, final String username,
			final int companyId, final Class<?> expected) {
		
		Class<?> caught;

		caught = null;

		try {
			
			super.authenticate(username);
			
			Company company = this.companyService.findOne(companyId);
			company.setName(name);
			company.setSurname(surname);
			company.setVatNumber(vatNumber);
			company.setAddress(address);
			company.setEmail(email);
			company.setPhoto(photo);
			company.setPhoneNumber(phoneNumber);
			company.setCommercialName(commercialName);

			this.companyService.save(company);

			this.companyService.flush();
			
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
