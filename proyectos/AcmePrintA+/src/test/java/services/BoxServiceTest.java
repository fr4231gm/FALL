
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Box;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BoxServiceTest extends AbstractTest {

	@Autowired
	BoxService			boxService;

	@Autowired
	ActorService		actorService;

	@Autowired
	UserAccountService	uaService;


	//Requierement: 19.4
	// An actor who is authenticated must be able to:
	// Manage his or her boxes

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear box sin Nick

	// Analysis of sentence coverage total(): Covered 36.5% 123/337 total instructions
	// Analysis of sentence coverage save(): Covered 48.6% 18/37 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 22/22 total instructions
	// Analysis of sentence coverage findBoxesByActor(): Covered 100.0% 7/7 total instructions

	// Analysis of data coverage: 45%

	@Test
	public void CreateEditBoxTestDriver() {

		final Object testingData[][] = {

			//TEST POSITIVO: 
			{
				"designer1", "ENDESA", null
			}, //Crear un box

			//TESTS NEGATIVOS:
			{
				"designer1", "", ConstraintViolationException.class
			}, //Intentar crear un box sin name

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.CreateEditBoxTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}

	}

	protected void CreateEditBoxTemplate(final String username, final String name, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			//Creamos el Data y le setteamos sus atributos
			final Box box = this.boxService.create();
			box.setName(name);

			//Lo guardamos
			final Box saved = this.boxService.save(box);
			final Actor principal = this.actorService.findByPrincipal();
			//Comprobamos que se ha creado
			Assert.isTrue(this.boxService.findBoxesByActor(principal.getId()).contains(saved));

			//Hacemos log out para la siguiente iteración
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requirement: 19.4
	// An actor who is authenticated must be able to:
	// delete a box

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar borrar un box que no es mio

	// Analysis of sentence coverage total(): Covered 22.6% 76/337 total instructions
	// Analysis of sentence coverage delete(): Covered 75% 54/72 total instructions
	// Analysis of sentence coverage findAll(): Covered 100.0% 4/4 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 10/10 total instructions

	// Analysis of data coverage: 100%

	@Test
	public void DeleteBoxTestDriver() {

		final Object testingData[][] = {

			//TEST POSITIVO: 

			{
				"customer2", "box25", null
			}, //Borrar mi box

			//TESTS NEGATIVOS:
			{
				"designer2", "box3", IllegalArgumentException.class
			}, //Intentar borrar un box que no es mio
			{
				"administrator0", "box1", IllegalArgumentException.class
			}, //Intentar borrar un box del sistema

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.DeleteBoxRedordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void DeleteBoxRedordTemplate(final String username, final String box, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			//Intentamos borrar el box y hacemos flush
			final Box m = this.boxService.findOne(this.getEntityId(box));

			this.boxService.delete(m);

			//Comprobamos que se ha borrado
			Assert.isTrue(!this.boxService.findAll().contains(m));

			//Hacemos log out para la siguiente iteración
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
