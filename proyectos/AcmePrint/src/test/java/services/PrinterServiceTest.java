
package services;

import java.util.Date;

import javax.transaction.Transactional;

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
import domain.Printer;
import forms.PrinterForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PrinterServiceTest extends AbstractTest {

	@Autowired
	InventoryService	inventoryService;

	@Autowired
	PrinterService		printerService;

	@Autowired
	CompanyService		companyService;


	// Requirement: 22.4
	// An actor who is authenticated as a company must be able to:
	//4. Manage his or her inventory,

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear un printer sin attachment
	//Intentar crear un printer con attachment en blanco
	//Intentar crear un printer con make en blanco
	//Intentar crear un printer sin make
	//Intentar crear un printer si soy un admin

	// Analysis of sentence coverage total: Covered 33% 149/451 total instructions
	// Analysis of sentence coverage reconstruct(): Covered 96.2% 100/104 total instructions
	// Analysis of sentence coverage save(): Covered 100% 42/42 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions

	// Analysis of printer coverage: 66,66%

	@Test
	public void CreateEditPrinterTestDriver() {

		final Object testingPart[][] = {

			//TEST POSITIVO: 
			{
				"company1", "APPLE", "IPHONE", "A really expensive thing", new Date(System.currentTimeMillis() - 36000000), new Date(System.currentTimeMillis() + 36000000), "https://www.amazon.com/samsung/printers", 250, 450, 600, 3.2, 1.8,
				"PLA, PETG, ACME", "https://www.google.es/coolphoto", true, null
			}, //Crear un printer

			//TESTS NEGATIVOS:
			{
				"admin", "SAMSUNG", "GALAXY", "A really worth it thing", new Date(System.currentTimeMillis() - 36000000), new Date(System.currentTimeMillis() + 36000000), "https://www.amazon.com/samsung/printers", 250, 450, 600, 3.2, 1.8,
				"PLA, PETG, ACME", "https://www.google.es/coolphoto", true, IllegalArgumentException.class
			}, //Crear un printer //Intentar crear un printer si soy un admin

		};

		for (int i = 0; i < testingPart.length; i++) {
			this.startTransaction();
			this.CreateEditPrinterTemplate((String) testingPart[i][0], (String) testingPart[i][1], (String) testingPart[i][2], (String) testingPart[i][3], (Date) testingPart[i][4], (Date) testingPart[i][5], (String) testingPart[i][6],
				(Integer) testingPart[i][7], (Integer) testingPart[i][8], (Integer) testingPart[i][9], (Double) testingPart[i][10], (Double) testingPart[i][11], (String) testingPart[i][12], (String) testingPart[i][13], (Boolean) testingPart[i][14],
				(Class<?>) testingPart[i][15]);
			this.rollbackTransaction();
		}

	}

	protected void CreateEditPrinterTemplate(final String username, final String make, final String model, final String description, final Date datePurchase, final Date warrantyDate, final String shoppingWebsite, final Integer dimensionX,
		final Integer dimensionY, final Integer dimensionZ, final Double extruderDiameter, final Double nozzle, final String materials, final String photo, final Boolean isActive, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			final Inventory c = this.inventoryService.findByPrincipal().iterator().next();

			//Creamos el Part y le setteamos sus atributos
			final PrinterForm printerForm = new PrinterForm();
			printerForm.setMake(make);
			printerForm.setModel(model);
			printerForm.setDescription(description);
			printerForm.setDatePurchase(datePurchase);
			printerForm.setWarrantyDate(warrantyDate);
			printerForm.setShoppingWebsite(shoppingWebsite);
			printerForm.setDimensionX(dimensionX);
			printerForm.setDimensionY(dimensionY);
			printerForm.setDimensionZ(dimensionZ);
			printerForm.setExtruderDiameter(extruderDiameter);
			printerForm.setNozzle(nozzle);
			printerForm.setMaterials(materials);
			printerForm.setPhoto(photo);
			printerForm.setIsActive(isActive);

			printerForm.setInventory(c);

			final BindingResult binding = new DataBinder(printerForm).getBindingResult();
			final Printer printer = this.printerService.reconstruct(printerForm, binding);

			//Lo guardamos y hacemos flush
			final Printer saved = this.printerService.save(printer, c.getId());
			this.printerService.flush();

			//Comprobamos que el inventory del company ahora tiene ese printer
			Assert.isTrue(this.inventoryService.findOne(c.getId()).getPrinters().contains(saved));

			//UPDATE
			this.printerService.save(saved, c.getId());
			this.printerService.flush();

			//Hacemos log out para la siguiente iteración
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	//Requirement: 22.4. 
	// An actor authenticated as Company must be able to:
	// Display the inventory of every company that he or she can display.
	// Delete their printer

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar borrar un printer como administrador
	//Intentar borrar un printer que no es mio

	// Analysis of sentence coverage total: Covered 11.8% 53/451 total instructions
	// Analysis of sentence coverage delete(): Covered 97.4% 37/38 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 9/9 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions

	// Analysis of printer coverage: 100%

	@Test
	public void DeletePrinterTestDriver() {

		final Object testingPart[][] = {

			//TEST POSITIVO: 

			{
				"company6", "printer6", null
			}, //Borrar mi printer

			//TESTS NEGATIVOS:
			{
				"admin", "printer1", IllegalArgumentException.class
			}, //Intentar borrar un printer como administrador

			{
				"company2", "printer6", IllegalArgumentException.class
			}, //Intentar borrar un printer que no es mio

		};

		for (int i = 0; i < testingPart.length; i++) {
			this.startTransaction();
			this.DeletePrinterTemplate((String) testingPart[i][0], (String) testingPart[i][1], (Class<?>) testingPart[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void DeletePrinterTemplate(final String username, final String printerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			//Intentamos borrar el printer y hacemos flush
			final Printer printer = this.printerService.findOne(this.getEntityId(printerId));

			final Inventory c = this.inventoryService.findByPrinterId(printer.getId());
			this.printerService.delete(printer);
			this.printerService.flush();

			//Comprobamos que el inventory del company ya no tiene ese printer
			Assert.isTrue(!this.inventoryService.findOne(c.getId()).getPrinters().contains(printer));

			//Hacemos log out para la siguiente iteración
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
