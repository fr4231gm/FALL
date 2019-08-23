package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Registration;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegistrationServiceTest extends AbstractTest {

	@Autowired
	private RegistrationService registrationService;

	@Test
	public void createRegistrationTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ super.getEntityId("conference1"), "author1", 123, 1, 22,
						"jose", "4111111111111111", "AMEX", null },

				// TEST NEGATIVO: Intenta crearlo un actor que no es autor
				{ super.getEntityId("conference1"), "admin", 123, 1, 22,
						"jose", "4111111111111111", "AMEX", IllegalArgumentException.class },

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.createRegistrationTemplate((int) testingData[i][0],
					(String) testingData[i][1], (Integer) testingData[i][2],
					(Integer) testingData[i][3], (Integer) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (Class<?>) testingData[i][8]);
			this.rollbackTransaction();
		}
	}

	protected void createRegistrationTemplate(int conferenceId,
			String username, Integer cVV, Integer expirationMonth,
			Integer expirationYear, String holder, String number, String make,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		Registration registration, saved;
		CreditCard c;

		try {

			super.authenticate(username);

			registration = this.registrationService.create(conferenceId);
			c = registration.getCreditCard();
			c.setCVV(cVV);
			c.setExpirationMonth(expirationMonth);
			c.setExpirationYear(expirationYear);
			c.setHolder(holder);
			c.setNumber(number);
			c.setMake(make);

			saved = this.registrationService.save(registration);

			Assert.isTrue(saved.getId() != 0);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
