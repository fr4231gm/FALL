package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.CreditCard;
import domain.Provider;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProviderServiceTest extends AbstractTest {

	@Autowired
	private ProviderService providerService;

	// Requierement: 9.1 An actor who is not authenticated must be able to:
	// Browse the list of providers and navigate to their items.
	
	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// No procede

	// Analysis of sentence coverage total: Covered 3.0% 13/434 total instructions
	// Analysis of sentence coverage findAll(): Covered 100.0% 6/6 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions
	
	// Analysis of data coverage: 100.0%

	@Test
	public void listProvidersTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ null } // Listar providers
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listProvidersTemplate((Class<?>) testingData[i][0]);
			this.rollbackTransaction();
		}
	}

	protected void listProvidersTemplate(final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			this.providerService.findAll();

			this.providerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	// Requierement: RF 9.3 An actor who is not authenticated must be able to:
	// Register to the system as a provider.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar registrarse como un Provider con el campo name vacio.

	// Analysis of sentence coverage total: Covered 19.1% 81/423 total instructions
	// Analysis of sentence coverage saveFirst(): Covered 100.0% 34/34 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 40/40 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions
			
	// Analysis of data coverage: 53.3%

		@Test
		public void registerProviderTestDriver() {

			final Object testingData[][] = {

			// TEST POSITIVO:
			{ "name", "surname", "make", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "provide", "provide", 
				123, 1, 19, "jose", "4111111111111111", "AMEX", null}, 

			// TEST NEGATIVO:
				{ "", "surname", "make", "A12345678", "http://www.google.es/photo.jpg", "123456789", "email@gmail.com", "address", "provider0", "provider0", 
					123, 1, 19, "jose", "4111111111111111", "AMEX",
					 ConstraintViolationException.class },

			};

			for (int i = 0; i < testingData.length; i++) {
				this.startTransaction();
				this.registerProviderTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
						(String) testingData[i][8], (String) testingData[i][9], (Integer) testingData[i][10], (Integer) testingData[i][11], (Integer) testingData[i][12],(String) testingData[i][13], (String) testingData[i][14], 
						(String) testingData[i][15],(Class<?>) testingData[i][16]);
				this.rollbackTransaction();
			}
		}

		protected void registerProviderTemplate(final String name,
				final String surname, final String make2, final String vatNumber, final String photo,
				final String phoneNumber, final String email, final String address,
				final String username, final String password,
				final Integer cVV, final Integer expirationMonth, final Integer expirationYear,
				final String holder, final String number, final String make,
				final Class<?> expected) {

			Class<?> caught;

			caught = null;

			try {
					final Provider provider = this.providerService.create(username, password);
					final CreditCard c = new CreditCard();
					provider.setName(name);
					provider.setSurname(surname);
					provider.setMake(make2);
					provider.setVatNumber(vatNumber);
					provider.setAddress(address);
					provider.setEmail(email);
					provider.setPhoto(photo);
					provider.setPhoneNumber(phoneNumber);
					provider.setIsSpammer(false);
					c.setCVV(cVV);
					c.setExpirationMonth(expirationMonth);
					c.setExpirationYear(expirationYear);
					c.setHolder(holder);
					c.setNumber(number);
					c.setMake(make);

				this.providerService.saveFirst(provider, c);

				this.providerService.flush();

			} catch (final Throwable oops) {
				caught = oops.getClass();
			}
			this.checkExceptions(expected, caught);
		}

}
