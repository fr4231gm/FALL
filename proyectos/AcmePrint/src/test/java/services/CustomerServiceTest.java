package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Customer;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerServiceTest extends AbstractTest {

	@Autowired
	private CustomerService customerService;

	// Requirement: RF 18. An actor who is not authenticated must be able to:
	// 1. Register to the system as a customer.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Intentar registrarse con el name vacio.
	// Intentar registrarse con el VAT Number vacio.

	// Analysis of sentence coverage total: Covered 22.3% 82/367 total instructions
	// Analysis of sentence coverage save(): Covered 71.4% 35/49 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 40/40 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 60.0%

	@Test
	public void registerCustomerTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "name", "middleName", "surname", "A12345678",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "customerTest",
						"customerTest", null },

				// TEST NEGATIVO:
				{ "", "middleName", "surname", "A12345678",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "customerTest",
						"customerTest", ConstraintViolationException.class },
			
				// TEST NEGATIVO:
				{ "name", "middleName", "surname", "",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "customerTest",
						"customerTest", ConstraintViolationException.class },		

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.registerCustomerTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8],
					(String) testingData[i][9], (Class<?>) testingData[i][10]);
			this.rollbackTransaction();
		}
	}

	protected void registerCustomerTemplate(final String name,
			final String middleName, final String surname,
			final String vatNumber, final String photo,
			final String phoneNumber, final String email, final String address,
			final String username, final String password,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			final Customer customer = this.customerService.create(username,
					password);

			customer.setName(name);
			customer.setMiddleName(middleName);
			customer.setSurname(surname);
			customer.setVatNumber(vatNumber);
			customer.setAddress(address);
			customer.setEmail(email);
			customer.setPhoto(photo);
			customer.setPhoneNumber(phoneNumber);
			customer.setIsSpammer(false);

			this.customerService.save(customer);

			this.customerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 19. An actor who is authenticated must be able to:
	// 2. Edit his or her personal data.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Editar los datos personales de un Customer estando registrado como otro
	// actor.
	// Editar campo name y dejarlo vacio.

	// Analysis of sentence coverage total: Covered 20.2% 74/367 total instructions
	// Analysis of sentence coverage save(): Covered 59.2% 29/49 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 51.0%

	@Test
	public void editCustomerTestDriver() {
		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "name", "surname", "middleName", "A12345678",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "customer1",
						super.getEntityId("customer1"), null },

				// TEST NEGATIVO:
				{ "name", "surname", "middleName", "A12345678",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "company1",
						super.getEntityId("customer1"),
						IllegalArgumentException.class },
						
				// TEST NEGATIVO:
				{ "", "surname", "middleName", "A12345678",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "customer1",
						super.getEntityId("customer1"), ConstraintViolationException.class },		

		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.editCustomerTemplate((String) testingData[i][0],(String) testingData[i][1], 
					(String) testingData[i][2], (String) testingData[i][3],
					(String) testingData[i][4], (String) testingData[i][5],
					(String) testingData[i][6], (String) testingData[i][7],
					(String) testingData[i][8],
					(int) testingData[i][9], (Class<?>) testingData[i][10]);
		this.rollbackTransaction();
	}

	protected void editCustomerTemplate(final String name, final String surname, final String middleName,
			final String vatNumber, final String photo,
			final String phoneNumber, final String email, final String address,
			final String username, final int customerId, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			super.authenticate(username);

			Customer customer = this.customerService.findOne(customerId);
			
			customer.setName(name);
			customer.setSurname(surname);
			customer.setMiddleName(middleName);
			customer.setVatNumber(vatNumber);
			customer.setAddress(address);
			customer.setEmail(email);
			customer.setPhoto(photo);
			customer.setPhoneNumber(phoneNumber);

			this.customerService.save(customer);

			this.customerService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
