
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Hacker;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HackerServiceTest extends AbstractTest {

	@Autowired
	private HackerService	hackerService;


	// Requierement: 7. An actor who is not authenticated must be able to:
	// 1. Register to the system as a hacker.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Intentar registrarse con el name vacio.
	
	// Analysis of sentence coverage total: Covered 21% 86/409 total instructions
	// Analysis of sentence coverage save(): Covered 73.6% 39/53 total instructions
	// Analysis of sentence coverage create(): Covered 100% 40/40 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
		
	// Analysis of data coverage: 53.3%

	@Test
	public void registerHackerTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"name", "surname", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "Company8", "Company8", null
			}, // Registrarse
				// correctamente

			// TEST NEGATIVO:
			{
				"", "surname", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "Company8", "Company8", ConstraintViolationException.class
			},
		// Intentar registrarse con el name vacio

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.registerHackerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (Class<?>) testingData[i][9]);
			this.rollbackTransaction();
		}
	}

	protected void registerHackerTemplate(final String name, final String surname, final String vatNumber, final String photo, final String phoneNumber, final String email, final String address, final String username, final String password,
		final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			final Hacker hacker = this.hackerService.create(username, password);
			hacker.setName(name);
			hacker.setSurname(surname);
			hacker.setVatNumber(vatNumber);
			hacker.setAddress(address);
			hacker.setEmail(email);
			hacker.setPhoto(photo);
			hacker.setPhoneNumber(phoneNumber);

			this.hackerService.save(hacker);

			this.hackerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 8. An actor who is authenticated must be able to:
	// 2. Edit his or her personal data.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Editar los datos personales de un Hacker estando registrado como otro
	// actor.

	// Analysis of sentence coverage total: Covered 17.4% 71/409 total instructions
	// Analysis of sentence coverage save(): Covered 49.1% 26/53 total instructions
	// Analysis of sentence coverage findHackerByUserAccount(): Covered 93.3% 14/15 total instructions
	// Analysis of sentence coverage findByPrincipal(): Covered 100% 13/13 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
		
	// Analysis of data coverage: 48.33%

	@Test
	public void editHackerTestDriver() {
		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"name", "surname", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "hacker1", super.getEntityId("hacker1"), null
			},

			// TEST NEGATIVO:
			{
				"name", "surname", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "company1", super.getEntityId("hacker1"), IllegalArgumentException.class
			},

		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.editHackerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (int) testingData[i][8], (Class<?>) testingData[i][9]);
		this.rollbackTransaction();
	}

	protected void editHackerTemplate(final String name, final String surname, final String vatNumber, final String photo, final String phoneNumber, final String email, final String address, final String username, final int hackerId,
		final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			super.authenticate(username);

			final Hacker hacker = this.hackerService.findOne(hackerId);
			hacker.setName(name);
			hacker.setSurname(surname);
			hacker.setVatNumber(vatNumber);
			hacker.setAddress(address);
			hacker.setEmail(email);
			hacker.setPhoto(photo);
			hacker.setPhoneNumber(phoneNumber);

			this.hackerService.save(hacker);

			this.hackerService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
