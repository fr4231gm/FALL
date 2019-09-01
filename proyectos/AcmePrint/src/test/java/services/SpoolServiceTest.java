
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import utilities.AbstractTest;
import domain.Inventory;
import domain.Spool;
import forms.SpoolForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SpoolServiceTest extends AbstractTest {

	@Autowired
	InventoryService	inventoryService;

	@Autowired
	SpoolService		spoolService;

	@Autowired
	CompanyService		companyService;


	//Requirement: 22.4
	// An actor who is authenticated as a company must be able to:
	// Manage his or her inventory,

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear un spool sin attachment
	//Intentar crear un spool con attachment en blanco
	//Intentar crear un spool con materialName en blanco
	//Intentar crear un spool sin materialName
	//Intentar crear un spool si soy un admin

	// Analysis of sentence coverage total: Covered 36.8% 123/334 total instructions
	// Analysis of sentence coverage reconstruct(): Covered 94.9% 74/78 total instructions
	// Analysis of sentence coverage save(): Covered 100% 42/42 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions

	// Analysis of part coverage: 66,66%

	@Test
	public void CreateEditSpareTestDriver() {

		final Object testingPart[][] = {

			//TEST POSITIVO: 
			{
				"company3", "PLA", "red", 1.2, 8.5, 8.5, 1.5, 250, null
			}, //Crear un spool

			//TESTS NEGATIVOS:
			{
				"company3", "PETG", null, 2.3, 8.5, 8.5, 2.5, 250, ConstraintViolationException.class
			}, //Intentar crear un spool sin color

			{
				"company3", "PLA", "", 3.2, 8.5, 8.5, 3.5, 250, ConstraintViolationException.class
			}, //Intentar crear un spool con color en blanco

			{
				"company3", "", "green", 1.6, 8.5, 8.5, 1.5, 250, ConstraintViolationException.class
			}, //Intentar crear un spool con titulo en blanco

			{
				"admin", "PETG", "blue", 0.6, 8.5, 8.5, 2.5, 250, IllegalArgumentException.class
			}
		//Intentar crear un spool si soy un admin

		};

		for (int i = 0; i < testingPart.length; i++) {
			this.startTransaction();
			this.CreateEditSpareTemplate((String) testingPart[i][0], (String) testingPart[i][1], (String) testingPart[i][2], (Double) testingPart[i][3], (Double) testingPart[i][4], (Double) testingPart[i][5], (Double) testingPart[i][6],
				(Integer) testingPart[i][7], (Class<?>) testingPart[i][8]);
			this.rollbackTransaction();
		}

	}

	protected void CreateEditSpareTemplate(final String username, final String materialName, final String color, final Double diameter, final Double length, final Double remainingLength, final Double weight, final Integer meltingTemperature,
		final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			final Inventory c = this.inventoryService.findByPrincipal().iterator().next();

			//Creamos el Part y le setteamos sus atributos
			final SpoolForm spoolForm = new SpoolForm();
			spoolForm.setMaterialName(materialName);
			spoolForm.setColor(color);
			spoolForm.setDiameter(diameter);
			spoolForm.setLength(length);
			spoolForm.setRemainingLength(remainingLength);
			spoolForm.setWeight(weight);
			spoolForm.setMeltingTemperature(meltingTemperature);
			spoolForm.setInventory(c);

			final BindingResult binding = new DataBinder(spoolForm).getBindingResult();
			final Spool spool = this.spoolService.reconstruct(spoolForm, binding);

			//Lo guardamos y hacemos flush
			final Spool saved = this.spoolService.save(spool, c.getId());
			this.spoolService.flush();

			//Comprobamos que el inventory del company ahora tiene ese part
			Assert.isTrue(this.inventoryService.findOne(c.getId()).getSpools().contains(saved));

			//UPDATE
			this.spoolService.save(saved, c.getId());
			this.spoolService.flush();

			//Hacemos log out para la siguiente iteración
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	//Requierement: 22.4. An actor authenticated as a company must be able to:
	// Display the inventory of every company that he or she can display.
	// Delete their spool

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar borrar un part como administrador
	//Intentar borrar un part que no es mio

	// Analysis of sentence coverage total: Covered 15.9% 53/334 total instructions
	// Analysis of sentence coverage delete(): Covered 97.4% 37/38 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 9/9 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions

	// Analysis of part coverage: 100%

	@Test
	public void DeleteSpoolTestDriver() {

		final Object testingPart[][] = {

			//TEST POSITIVO: 

			{
				"company1", "spool1", null
			}, //Borrar mi part

			//TESTS NEGATIVOS:
			{
				"admin", "spool1", IllegalArgumentException.class
			}, //Intentar borrar un part como administrador

		// {"company2", "spool1",IllegalArgumentException.class}, //Intentar borrar un part que no es mio

		};

		for (int i = 0; i < testingPart.length; i++) {
			this.startTransaction();
			this.DeleteSpoolTemplate((String) testingPart[i][0], (String) testingPart[i][1], (Class<?>) testingPart[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void DeleteSpoolTemplate(final String username, final String spoolId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			//Intentamos borrar el part y hacemos flush
			final Spool spool = this.spoolService.findOne(this.getEntityId(spoolId));

			final Inventory c = this.inventoryService.findBySpoolId(spool.getId());
			this.spoolService.delete(spool);
			this.spoolService.flush();

			//Comprobamos que el inventory del company ya no tiene ese part
			Assert.isTrue(!this.inventoryService.findOne(c.getId()).getSpools().contains(spool));

			//Hacemos log out para la siguiente iteración
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
