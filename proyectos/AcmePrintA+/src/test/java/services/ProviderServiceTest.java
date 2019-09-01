package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Provider;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProviderServiceTest extends AbstractTest {

	@Autowired
	private ProviderService providerService;

	// Requirement: RF 18. An actor who is not authenticated must be able to:
	// 1. Register to the system as a provider.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar registrarse con el name vacio.
	// Intentar registrarse con el VAT Number vacio.

	// Analysis of sentence coverage total: Covered 19.3% 82/425 total instructions
	// Analysis of sentence coverage save(): Covered 71.4% 35/49 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 40/40 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 59.09%

	@Test
	public void registerProviderTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "name", "middleName", "surname", "make", "A12345678",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "providerTest",
						"providerTest", null },

				// TEST NEGATIVO:
				{ "", "middleName", "surname", "make", "A12345678",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "providerTest",
						"providerTest", ConstraintViolationException.class },
						
				// TEST NEGATIVO:
				{ "name", "middleName", "surname", "make", "",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "providerTest",
						"providerTest", ConstraintViolationException.class },		

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.registerProviderTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8],
					(String) testingData[i][9], (String) testingData[i][10],
					(Class<?>) testingData[i][11]);
			this.rollbackTransaction();
		}
	}

	protected void registerProviderTemplate(final String name,
			final String middleName, final String surname, final String make,
			final String vatNumber, final String photo,
			final String phoneNumber, final String email, final String address,
			final String username, final String password,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			final Provider provider = this.providerService.create(username,
					password);

			provider.setName(name);
			provider.setMiddleName(middleName);
			provider.setSurname(surname);
			provider.setMake(make);
			provider.setVatNumber(vatNumber);
			provider.setAddress(address);
			provider.setEmail(email);
			provider.setPhoto(photo);
			provider.setPhoneNumber(phoneNumber);
			provider.setIsSpammer(false);

			this.providerService.save(provider);

			this.providerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 19. An actor who is authenticated must be able to:
	// 2. Edit his or her personal data.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Editar los datos personales de una Provider estando registrado como otro
	// actor.
	// Editar campo name y dejarlo vacio.

	// Analysis of sentence coverage total: Covered 17.4% 74/425 total instructions
	// Analysis of sentence coverage save(): Covered 59.2% 29/49 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 54.04%

	@Test
	public void editProviderTestDriver() {
		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "name", "make", "surname", "middleName",
						"A12345678", "http://www.google.es/photo.jpg",
						"123456789", "email@gmail.com", "address", "provider1",
						super.getEntityId("provider1"), null },

				// TEST NEGATIVO:
				{ "name", "make", "surname", "middleName",
						"A12345678", "http://www.google.es/photo.jpg",
						"123456789", "email@gmail.com", "address", "designer1",
						super.getEntityId("provider1"),
						IllegalArgumentException.class },
						
				// TEST NEGATIVO:
				{ "", "make", "surname", "middleName",
						"A12345678", "http://www.google.es/photo.jpg",
						"123456789", "email@gmail.com", "address", "provider1",
						super.getEntityId("provider1"),
						ConstraintViolationException.class },		

		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.editProviderTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8],
					(String) testingData[i][9], (int) testingData[i][10],
					(Class<?>) testingData[i][11]);
		this.rollbackTransaction();
	}

	protected void editProviderTemplate(final String name,
			final String make, final String surname,
			final String middleName, final String vatNumber,
			final String photo, final String phoneNumber, final String email,
			final String address, final String username, final int providerId,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			super.authenticate(username);

			Provider provider = this.providerService.findOne(providerId);

			provider.setName(name);
			provider.setMake(make);
			provider.setSurname(surname);
			provider.setMiddleName(middleName);
			provider.setVatNumber(vatNumber);
			provider.setAddress(address);
			provider.setEmail(email);
			provider.setPhoto(photo);
			provider.setPhoneNumber(phoneNumber);

			this.providerService.save(provider);

			this.providerService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
