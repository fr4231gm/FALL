package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Designer;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DesignerServiceTest extends AbstractTest {

	@Autowired
	private DesignerService designerService;

	// Requirement: RF 18. An actor who is not authenticated must be able to:
	// 1. Register to the system as a designer.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Intentar registrarse con el name vacio.
	// Intentar registrarse con el VAT Number vacio.

	// Analysis of sentence coverage total: Covered 20.0% 82/409 total instructions
	// Analysis of sentence coverage save(): Covered 71.4% 35/49 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 40/40 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 60.00%

	@Test
	public void registerDesignerTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "name", "middleName", "surname", "A12345678",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "designerTest",
						"designerTest", null },

				// TEST NEGATIVO:
				{ "", "middleName", "surname", "A12345678",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "designerTest",
						"designerTest", ConstraintViolationException.class },
						
				// TEST NEGATIVO:
				{ "name", "middleName", "surname", "",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "designerTest",
						"designerTest", ConstraintViolationException.class },		

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.registerDesignerTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8],
					(String) testingData[i][9], (Class<?>) testingData[i][10]);
			this.rollbackTransaction();
		}
	}

	protected void registerDesignerTemplate(final String name,
			final String middleName, final String surname,
			final String vatNumber, final String photo,
			final String phoneNumber, final String email, final String address,
			final String username, final String password,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			final Designer designer = this.designerService.create(username,
					password);

			designer.setName(name);
			designer.setMiddleName(middleName);
			designer.setSurname(surname);
			designer.setVatNumber(vatNumber);
			designer.setAddress(address);
			designer.setEmail(email);
			designer.setPhoto(photo);
			designer.setPhoneNumber(phoneNumber);
			designer.setIsSpammer(false);

			this.designerService.save(designer);

			this.designerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requirement: RF 19. An actor who is authenticated must be able to:
	// 2. Edit his or her personal data.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Editar los datos personales de un Designer estando registrado como otro
	// actor.
	// Editar campo name y dejarlo vacio.

	// Analysis of sentence coverage total: Covered 18.1% 74/409 total instructions
	// Analysis of sentence coverage save(): Covered 59.2% 29/49 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 51.0%

	@Test
	public void editDesignerTestDriver() {
		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "name", "surname", "middleName", "A12345678",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "designer1",
						super.getEntityId("designer1"), null },

				// TEST NEGATIVO:
				{ "name", "surname", "middleName", "A12345678",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "company1",
						super.getEntityId("designer1"),
						IllegalArgumentException.class },
						
				// TEST NEGATIVO:
				{ "", "surname", "middleName", "A12345678",
						"http://www.google.es/photo.jpg", "123456789",
						"email@gmail.com", "address", "designer1",
						super.getEntityId("designer1"),
						ConstraintViolationException.class },				

		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.editDesignerTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8],
					(int) testingData[i][9], (Class<?>) testingData[i][10]);
		this.rollbackTransaction();
	}

	protected void editDesignerTemplate(final String name,
			final String surname, final String middleName,
			final String vatNumber, final String photo,
			final String phoneNumber, final String email, final String address,
			final String username, final int designerId, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			super.authenticate(username);

			Designer designer = this.designerService.findOne(designerId);

			designer.setName(name);
			designer.setSurname(surname);
			designer.setMiddleName(middleName);
			designer.setVatNumber(vatNumber);
			designer.setAddress(address);
			designer.setEmail(email);
			designer.setPhoto(photo);
			designer.setPhoneNumber(phoneNumber);

			this.designerService.save(designer);

			this.designerService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requirement: 18.2 An actor who is not authenticated must be able to:
	// List the posts published and navigate to the profile of the corresponding designer.

	// In the case of negative tests, the business rule that is intended to be broken:
	// No procede

	// Analysis of sentence coverage total: Covered 4.5% 18/401 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 20.0%

	@Test
	public void displayDesignerTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{"designer1", null } };
				
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.displayDesignerTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void displayDesignerTemplate(final String designerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			this.designerService.findOne(this.getEntityId(designerId));

			this.designerService.flush();


		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
