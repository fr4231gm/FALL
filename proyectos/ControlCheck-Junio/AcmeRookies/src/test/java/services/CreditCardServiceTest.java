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

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	@Autowired
	private CreditCardService creditCardService;


	//Requierement: this is there is no direct requirement but it is intuited in the requirements
	//	For every actor, the system must store: ... a valid credit card.

	//Edit CreditCard
	// 1. Editar una tarjeta de credito de una company
	// 1. Editar una tarjeta de credito de un rookie
	// In the case of negative tests, the business rule that is intended to be broken:
	// 3. Editar una tarjeta con expirationMonth null
	// 4. Editar una tarjeta con number invalido
	
	// Analysis of sentence coverage total: Covered 15.1% 42/279 total instructions
	// Analysis of sentence coverage save(): Covered 96.3% 26/27 total instructions
	// Analysis of sentence coverage findCreditCardByActorId(): Covered 100% 9/9 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
	
	// Analysis of data coverage: 59.6%
	@Test
	public void editCreditCardTestDriver() {

		final Object testingData[][] = {
				
			// 1.TEST POSITIVO:,
			{"Juan","VISA","4508789617365751",8,23,785,"company1", null},
			// 2.TEST POSITIVO:
			{"Ramon","AMEX","4786864944775510",8,23,785,"rookie1", null},
			// 4.TEST NEGATIVO:
			{"Juan","VISA","4508789617365751",null,23,785,"company1", ConstraintViolationException.class},
			// 5.TEST NEGATIVO:
			{"Juan","VISA","4780000044775510",8,23,785,"rookie1", ConstraintViolationException.class},
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.editCreditCardTemplate((String) testingData[i][0],(String) testingData[i][1],(String) testingData[i][2],(Integer) testingData[i][3],(Integer) testingData[i][4], (Integer) testingData[i][5],(String) testingData[i][6],(Class<?>) testingData[i][7]);
			this.rollbackTransaction();
		}
	}

	//Template
	private void editCreditCardTemplate(String holder, String make,
			String number, Integer expirationMonth, Integer expirationYear, Integer CVV, String username,
			Class<?> expected) {
			Class<?> caught;

			caught = null;

			try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			final CreditCard cc = this.creditCardService.findCreditCardByActorId(this.getEntityId(username));
			cc.setHolder(holder);
			cc.setMake(make);
			cc.setNumber(number);
			cc.setExpirationMonth(expirationMonth);
			cc.setExpirationYear(expirationYear);
			cc.setCVV(CVV);
			
			this.creditCardService.save(cc);
			this.creditCardService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	}
	
	
	

