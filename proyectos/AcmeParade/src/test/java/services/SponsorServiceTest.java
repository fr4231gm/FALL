
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Sponsor;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorServiceTest extends AbstractTest {

	@Autowired
	private SponsorService	sponsorService;

	//Requierement: 15. An actor who is not authenticated must be able to:
	//1. Register as a sponsor.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar registrarse con el name vacio
	//Intentar registrarse con el email vacio
	//Intentar registrarse con el surname vacio
	// Intenta registrarse con foto no url
	
	//Analysis of sentence coverage: Covered 100%
															
	//Analysis of data coverage: Covered 299 /299 total instructions
	
	@Test
	public void registerSponsorTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"address", "email@gmail.com", "http://www.google.es/photo.jpg", "123456789", "middleName", "name", "surname", "Sponsor8", "Sponsor8", null
			}, // Registrarse
				// correctamente

			// TEST NEGATIVO:
			{
				"address", "email@gmail.com", "http://www.google.es/photo.jpg", "123456789", "middleName", "", "surname", "Sponsor8", "Sponsor8", ConstraintViolationException.class
			},
			//Intentar registrarse con el name vacio

			// TEST NEGATIVO:
			{
				"address", "", "http://www.google.es/photo.jpg", "123456789", "middleName", "name", "surname", "Sponsor8", "Sponsor8", ConstraintViolationException.class
			},
			//Intentar registrarse con el email vacio

			// TEST NEGATIVO:
			{
				"address", "email@gmail.com", "http://www.google.es/photo.jpg", "123456789", "middleName", "name", "", "Sponsor8", "Sponsor8", ConstraintViolationException.class
			},
			//Intentar registrarse con el surname vacio

			// TEST NEGATIVO:
			{
				"address", "email@gmail.com", "photo.jpg", "123456789", "middleName", "name", "surname", "Sponsor8", "Sponsor8", ConstraintViolationException.class
			}
		// Intenta registrarse con foto no url
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.registerSponsorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (Class<?>) testingData[i][9]);
			this.rollbackTransaction();
		}
	}

	protected void registerSponsorTemplate(final String address, final String email, final String photo, final String phoneNumber, final String middleName, final String name, final String surname, final String username, final String password,
		final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			final Sponsor sponsor = this.sponsorService.create(username, password);
			sponsor.setAddress(address);
			sponsor.setEmail(email);
			sponsor.setPhoto(photo);
			sponsor.setPhoneNumber(phoneNumber);
			sponsor.setMiddleName(middleName);
			sponsor.setName(name);
			sponsor.setSurname(surname);

			this.sponsorService.save(sponsor);
			this.sponsorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
