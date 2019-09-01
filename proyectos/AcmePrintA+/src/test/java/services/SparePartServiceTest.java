
package services;

import java.util.Date;

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
import domain.SparePart;
import forms.SparePartForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SparePartServiceTest extends AbstractTest {

	@Autowired
	InventoryService	inventoryService;

	@Autowired
	SparePartService	sparePartService;

	@Autowired
	CompanyService		companyService;


	//Requirement:
	//22. An actor who is authenticated as a company must be able to:
	//4. Manage his or her inventory,

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear un spare part sin attachment
	//Intentar crear un spare part con attachment en blanco
	//Intentar crear un spare part con name en blanco
	//Intentar crear un spare part sin name
	//Intentar crear un spare part si soy un admin

	// Analysis of sentence coverage total: Covered 24.7% 64/259 total instructions
	// Analysis of sentence coverage save(): Covered 81.1% 43/53 total instructions
	// Analysis of sentence coverage create(): Covered 100% 14/14 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions

	// Analysis of part coverage: 66,66%

	@Test
	public void CreateEditSpareTestDriver() {

		final Object testingPart[][] = {

			//TEST POSITIVO: 
			{
				"company3", "name", "description", 8.5, new Date(), "https://www.google.es", null
			}, //Crear un spare part

			//TESTS NEGATIVOS:
			{
				"company3", "name", null, 5.8, new Date(), "https://www.google.es", ConstraintViolationException.class
			}, //Intentar crear un spare part sin description

			{
				"company3", "name", "", 8.9, new Date(), "https://www.google.es", ConstraintViolationException.class
			}, //Intentar crear un spare part con description en blanco

			{
				"company3", "", "description", 3.2, new Date(), "https://www.google.es", ConstraintViolationException.class
			}, //Intentar crear un spare part con titulo en blanco

			{
				"admin", "name", "description", 8.0, new Date(), "https://www.google.es", IllegalArgumentException.class
			}
		//Intentar crear un spare part si soy un admin

		};

		for (int i = 0; i < testingPart.length; i++) {
			this.startTransaction();
			this.CreateEditSpareTemplate((String) testingPart[i][0], (String) testingPart[i][1], (String) testingPart[i][2], (Double) testingPart[i][3], (Date) testingPart[i][4], (String) testingPart[i][5], (Class<?>) testingPart[i][6]);
			this.rollbackTransaction();
		}

	}

	protected void CreateEditSpareTemplate(final String username, final String name, final String description, final Double purchasePrice, final Date purchaseDate, final String photo, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			final Inventory c = this.inventoryService.findByPrincipal().iterator().next();

			//Creamos el Part y le setteamos sus atributos
			final SparePartForm sparePartForm = new SparePartForm();
			sparePartForm.setName(name);
			sparePartForm.setDescription(description);
			sparePartForm.setPurchasePrice(purchasePrice);
			sparePartForm.setPurchaseDate(purchaseDate);
			sparePartForm.setPhoto(photo);
			sparePartForm.setInventory(c);

			final BindingResult binding = new DataBinder(sparePartForm).getBindingResult();
			final SparePart sparePart = this.sparePartService.reconstruct(sparePartForm, binding);

			//Lo guardamos y hacemos flush
			final SparePart saved = this.sparePartService.save(sparePart, c.getId());
			this.sparePartService.flush();

			//Comprobamos que el inventory del company ahora tiene ese part
			Assert.isTrue(this.inventoryService.findOne(c.getId()).getSpareParts().contains(saved));

			//UPDATE
			this.sparePartService.save(saved, c.getId());
			this.sparePartService.flush();

			//Hacemos log out para la siguiente iteración
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	//Requirement: 22.4. An actor authenticated as a company must be able to:
	// Display the inventory of every company that he or she can display.
	// Delete their spare part

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar borrar un part como administrador
	//Intentar borrar un part que no es mio

	// Analysis of sentence coverage total: Covered 20.8% 53/255 total instructions
	// Analysis of sentence coverage delete(): Covered 97.4% 37/38 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 9/9 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions

	// Analysis of part coverage: 100%

	@Test
	public void DeleteSparePartTestDriver() {

		final Object testingPart[][] = {

			//TEST POSITIVO: 

			{
				"company1", "sparePart1", null
			}, //Borrar mi part

			//TESTS NEGATIVOS:
			{
				"admin", "sparePart1", IllegalArgumentException.class
			}, //Intentar borrar un part como administrador

		// {"company2", "sparePart1",IllegalArgumentException.class}, //Intentar borrar un part que no es mio

		};

		for (int i = 0; i < testingPart.length; i++) {
			this.startTransaction();
			this.DeleteSparePartTemplate((String) testingPart[i][0], (String) testingPart[i][1], (Class<?>) testingPart[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void DeleteSparePartTemplate(final String username, final String sparePartId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			//Intentamos borrar el part y hacemos flush
			final SparePart sparePart = this.sparePartService.findOne(this.getEntityId(sparePartId));

			final Inventory c = this.inventoryService.findBySparePartId(sparePart.getId());
			this.sparePartService.delete(sparePart);
			this.sparePartService.flush();

			//Comprobamos que el inventory del company ya no tiene ese part
			Assert.isTrue(!this.inventoryService.findOne(c.getId()).getSpareParts().contains(sparePart));

			//Hacemos log out para la siguiente iteración
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
