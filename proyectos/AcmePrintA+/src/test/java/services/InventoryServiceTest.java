
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import utilities.AbstractTest;
import domain.Inventory;
import domain.Printer;
import domain.Spool;
import forms.PrinterForm;
import forms.SpoolForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class InventoryServiceTest extends AbstractTest {

	@Autowired
	InventoryService	inventoryService;

	@Autowired
	SpoolService		spoolService;

	@Autowired
	CompanyService		companyService;

	@Autowired
	PrinterService		printerService;


	// Requirement: 22.4
	// positive
	// Intentar crear un inventory como company
	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Intentar crear un inventory como customer

	// Analysis of sentence coverage total: Covered 26.9% 108/402 total
	// instructions
	// Analysis of sentence coverage findOne(): Covered 95.2% 20/21 total
	// instructions
	// Analysis of sentence coverage findByPricipal(): Covered 100% 15/15 total
	// instructions

	// Analysis of data coverage: 10%

	@Test
	public void CreateEditInventoryTestDriver() {
		// UC19-Crear y editar inventory
		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"company1", "PLA", "red", 1.2, 8.5, 8.5, 1.5, 250, "APPLE", "IPHONE", "A really expensive thing", new Date(System.currentTimeMillis() - 36000000), new Date(System.currentTimeMillis() + 36000000), "https://www.amazon.com/samsung/printers",
				250, 450, 600, 3.2, 1.8, "PLA, PETG, ACME", "https://www.google.es/coolphoto", true, null
			}, // Crear
				// un
				// inventory

			// TESTS NEGATIVOS:
			{
				"customer1", "PLA", "red", 1.2, 8.5, 8.5, 1.5, 250, "APPLE", "IPHONE", "A really expensive thing", new Date(System.currentTimeMillis() - 36000000), new Date(System.currentTimeMillis() + 36000000), "https://www.amazon.com/samsung/printers",
				250, 450, 600, 3.2, 1.8, "PLA, PETG, ACME", "https://www.google.es/coolphoto", true, IllegalArgumentException.class
			}, // Crear un spool

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();

			this.CreateInventoryTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6],
				(Integer) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (Date) testingData[i][11], (Date) testingData[i][12], (String) testingData[i][13], (Integer) testingData[i][14],
				(Integer) testingData[i][15], (Integer) testingData[i][16], (Double) testingData[i][17], (Double) testingData[i][18], (String) testingData[i][19], (String) testingData[i][20], (Boolean) testingData[i][21], (Class<?>) testingData[i][22]);
			this.rollbackTransaction();
		}
	}

	protected void CreateInventoryTemplate(final String username, final String materialName, final String color, final Double diameter, final Double length, final Double remainingLength, final Double weight, final Integer meltingTemperature,
		final String make, final String model, final String description, final Date datePurchase, final Date warrantyDate, final String shoppingWebsite, final Integer dimensionX, final Integer dimensionY, final Integer dimensionZ,
		final Double extruderDiameter, final Double nozzle, final String materials, final String photo, final Boolean isActive,

		final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			super.authenticate(username);
			final Inventory inventory = this.inventoryService.create();

			// Creamos el Inventory y le setteamos sus atributos
			final SpoolForm spoolForm = new SpoolForm();
			spoolForm.setMaterialName(materialName);
			spoolForm.setColor(color);
			spoolForm.setDiameter(diameter);
			spoolForm.setLength(length);
			spoolForm.setRemainingLength(remainingLength);
			spoolForm.setWeight(weight);
			spoolForm.setMeltingTemperature(meltingTemperature);

			final BindingResult binding = new DataBinder(spoolForm).getBindingResult();
			final Spool spool = this.spoolService.reconstruct(spoolForm, binding);

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

			final BindingResult printerbinding = new DataBinder(printerForm).getBindingResult();
			final Printer printer = this.printerService.reconstruct(printerForm, printerbinding);

			inventory.getSpools().add(spool);
			inventory.getPrinters().add(printer);

			// Saving
			this.inventoryService.save(inventory);
			this.inventoryService.flush();
			this.spoolService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
