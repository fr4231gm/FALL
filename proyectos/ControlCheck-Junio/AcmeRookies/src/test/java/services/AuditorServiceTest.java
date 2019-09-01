package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Auditor;
import domain.CreditCard;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditorServiceTest extends AbstractTest{
	
	@Autowired
	private AuditorService auditorService;
	
	// Requierement: RF 4.2 An actor who is authenticated as an administrator must be able to:
	// Create user accounts for new auditors.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar registrarse con el campo name vacio.

	// Analysis of sentence coverage total: Covered 22.2% 93/419 total instructions
	// Analysis of sentence coverage saveFirst(): Covered 100.0% 40/40 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 46/46 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions
			
	// Analysis of data coverage: 53.3%
	
	@Test
	public void registerAuditorTestDriver() {

		final Object testingData[][] = {


				// TEST POSITIVO:
				{ "admin", "name", "surname", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "provide", "provide", 
					123, 1, 19, "jose", "4111111111111111", "AMEX", null}, 

				// TEST NEGATIVO:
					{ "admin", "", "surname", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "provider0", "provider0", 
						123, 1, 19, "jose", "4111111111111111", "AMEX",
						 ConstraintViolationException.class },

				};
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.registerAuditorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
					(String) testingData[i][8], (String) testingData[i][9], (Integer) testingData[i][10], (Integer) testingData[i][11], (Integer) testingData[i][12],(String) testingData[i][13], (String) testingData[i][14], 
					(String) testingData[i][15],(Class<?>) testingData[i][16]);
			this.rollbackTransaction();
		}
	}

	protected void registerAuditorTemplate(final String admin, final String name,
			final String surname, final String vatNumber, final String photo,
			final String phoneNumber, final String email, final String address,
			final String username, final String password,final Integer cVV, final Integer expirationMonth, final Integer expirationYear,
			final String holder, final String number, final String make,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			super.authenticate(admin);
			final Auditor auditor = this.auditorService.create(username, password);
			final CreditCard c = new CreditCard();
			auditor.setName(name);
			auditor.setSurname(surname);
			auditor.setVatNumber(vatNumber);
			auditor.setAddress(address);
			auditor.setEmail(email);
			auditor.setPhoto(photo);
			auditor.setPhoneNumber(phoneNumber);
			auditor.setIsSpammer(false);
			c.setCVV(cVV);
			c.setExpirationMonth(expirationMonth);
			c.setExpirationYear(expirationYear);
			c.setHolder(holder);
			c.setNumber(number);
			c.setMake(make);

			this.auditorService.saveFirst(auditor, c);

			this.auditorService.flush();
			
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
}
