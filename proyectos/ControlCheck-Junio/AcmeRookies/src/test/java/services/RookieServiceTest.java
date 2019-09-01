package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Rookie;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RookieServiceTest extends AbstractTest {

	@Autowired
	private RookieService	rookieService;


	// Requierement: 7. An actor who is not authenticated must be able to:
	// 1. Register to the system as a rookie.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Intentar registrarse con el name vacio.
	
	// Analysis of sentence coverage total: Covered 22.7% 93/410 total instructions
	// Analysis of sentence coverage saveFirst(): Covered 100.0% 46/46 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 40/40 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions
		
	// Analysis of data coverage: 53.3%

	@Test
	public void registerRookieTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"name", "surname", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "rookiebalboa", "rookiebalboa", 
				123, 1, 19, "jose", "4111111111111111", "AMEX", null
			}, 
			
			 // TEST NEGATIVO:
			{
				"", "surname", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "RookieTheRock", "RookieTheRock", 
				123, 1, 19, "jose", "4111111111111111", "AMEX", ConstraintViolationException.class
			}, 

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.registerRookieTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8], (Integer) testingData[i][9], (Integer) testingData[i][10], (Integer) testingData[i][11],(String) testingData[i][12], (String) testingData[i][13], 
					(String) testingData[i][14],(Class<?>) testingData[i][15]);
			this.rollbackTransaction();
		}
	}
	
	protected void registerRookieTemplate(final String name, final String surname, final String vatNumber, final String photo, final String phoneNumber, final String email, final String address, final String username, final String password,
		final Integer cVV, final Integer expirationMonth, final Integer expirationYear,
		final String holder, final String number, final String make, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			//final Rookie rookie = this.rookieService.create(username, password);
			final Rookie rookie = this.rookieService.create(username, password);
			final CreditCard c = new CreditCard();
			rookie.setName(name);
			rookie.setSurname(surname);
			rookie.setVatNumber(vatNumber);
			rookie.setAddress(address);
			rookie.setEmail(email);
			rookie.setPhoto(photo);
			rookie.setPhoneNumber(phoneNumber);
			rookie.setIsSpammer(false);
			c.setCVV(cVV);
			c.setExpirationMonth(expirationMonth);
			c.setExpirationYear(expirationYear);
			c.setHolder(holder);
			c.setNumber(number);
			c.setMake(make);
			
			this.rookieService.saveFirst(rookie, c);
			//this.rookieService.save(rookie);

			this.rookieService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 8. An actor who is authenticated must be able to:
	// 2. Edit his or her personal data.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Editar los datos personales de un Rookie estando registrado como otro
	// actor.

	// Analysis of sentence coverage total: Covered 17.4% 71/409 total instructions
	// Analysis of sentence coverage save(): Covered 49.1% 26/53 total instructions
	// Analysis of sentence coverage findRookieByUserAccount(): Covered 93.3% 14/15 total instructions
	// Analysis of sentence coverage findByPrincipal(): Covered 100% 13/13 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
		
	// Analysis of data coverage: 48.33%

	@Test
	public void editRookieTestDriver() {
		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"name", "surname", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "rookie1", super.getEntityId("rookie1"), null
			},

			// TEST NEGATIVO:
			{
				"name", "surname", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "company1", super.getEntityId("rookie1"), IllegalArgumentException.class
			},

		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.editRookieTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (int) testingData[i][8], (Class<?>) testingData[i][9]);
		this.rollbackTransaction();
	}

	protected void editRookieTemplate(final String name, final String surname, final String vatNumber, final String photo, final String phoneNumber, final String email, final String address, final String username, final int rookieId,
		final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			super.authenticate(username);

			final Rookie rookie = this.rookieService.findOne(rookieId);
			rookie.setName(name);
			rookie.setSurname(surname);
			rookie.setVatNumber(vatNumber);
			rookie.setAddress(address);
			rookie.setEmail(email);
			rookie.setPhoto(photo);
			rookie.setPhoneNumber(phoneNumber);

			this.rookieService.save(rookie);

			this.rookieService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
