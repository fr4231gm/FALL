
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Request;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RequestServiceTest extends AbstractTest {

	@Autowired
	RequestService		requestService;

	@Autowired
	UserAccountService	uaService;


	//Requierement:
	// 22 An actor who is authenticated as a company must be able to:
	// 6. Manage his or her print spooler

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear request sin Nick

	// Analysis of sentence coverage total(): Covered 31.5% 34/108 total instructions
	// Analysis of sentence coverage save(): Covered 100% 14/14 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 6/6 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions

	// Analysis of data coverage: 45%

	@Test
	public void CreateEditRequestTestDriver() {

		final Object testingData[][] = {

			//TEST POSITIVO: 
			{
				"company1", 300, 100, 1.5, null
			//Crear una request
			},
			//TESTS NEGATIVOS:
			{
				"customer1", 10000, 500, 4.5, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.CreateEditRequestTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (Double) testingData[i][3], (Class<?>) testingData[i][4]);
			this.rollbackTransaction();
		}

	}

	protected void CreateEditRequestTemplate(final String username, final Integer hotbedTemp, final Integer extruderTemp, final Double layerHeight, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			//Creamos el Data y le setteamos sus atributos
			final Request request = this.requestService.create();
			request.setExtruderTemp(extruderTemp);
			request.setHotbedTemp(hotbedTemp);
			request.setLayerHeight(layerHeight);

			//Lo guardamos
			final Request saved = this.requestService.save(request);
			//Comprobamos que se ha creado
			Assert.isTrue(this.requestService.findOne(saved.getId()) != null);

			//Hacemos log out para la siguiente iteración
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
