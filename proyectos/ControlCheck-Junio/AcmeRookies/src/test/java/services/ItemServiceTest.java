package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Item;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ItemServiceTest extends AbstractTest {

	@Autowired
	private ItemService itemService;

	// Requierement: 9.2 An actor who is not authenticated must be able to:
	// Browse the list of items and navigate to their providers.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// No procede

	// Analysis of sentence coverage total: Covered 12.5% 11/88 total instructions
	// Analysis of sentence coverage findAll(): Covered 100.0% 4/4 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 100.0%

	@Test
	public void listItemsTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ null } // Listar items
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listItemsTemplate((Class<?>) testingData[i][0]);
			this.rollbackTransaction();
		}
	}

	protected void listItemsTemplate(final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			this.itemService.findAll();

			this.itemService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: RF-10.1 An actor who is authenticated as a provider must be
	// able to:
	// Manage his or her catalogue of items, which includes listing, showing,
	// creating, up-dating, and deleting them.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Intentar que un Actor que no existe muestre un item.

	// Analysis of sentence coverage total: Covered 21.6% 19/88 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 12/12 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 100.0%

	@Test
	public void displayItemTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ "provider1", "item2", null },
		
		// TEST NEGATIVO:
		{ "auditor11", "item1", IllegalArgumentException.class }
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.displayItemTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void displayItemTemplate(final String username,
			final String itemId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.itemService.findOne(this.getEntityId(itemId));

			this.itemService.flush();

			// Hacemos log out para la siguiente iteracion
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: 10.1 An actor who is authenticated as a provider must be
	// able to:
	// Manage his or her catalogue of items, which includes listing, showing,
	// creating, up-dating, and deleting them.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Intentar listar Items estando logeado como un actor inexistente.

	// Analysis of sentence coverage total: Covered 15.4% 14/91 total instructions
	// Analysis of sentence coverage findItemsByProvider(): Covered 100.0% 7/7 instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 20.0%

	@Test
	public void listItemsByActorTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ "provider1", super.getEntityId("provider1"),null }, // Listar items
		
		// TEST NEGATIVO:
		{ "rookie21", super.getEntityId("rookie2"), IllegalArgumentException.class}
		
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listItemsByActorTemplate((String) testingData[i][0],
					(int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void listItemsByActorTemplate(final String username,
			final int providerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.itemService.findItemsByProvider(providerId);

			this.itemService.flush();

			// Hacemos log out para la siguiente iteracion
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: 10.1 An actor who is authenticated as a provider must be able to:
	// Manage his or her catalogue of items, which includes listing, showing,
	// creating, up-dating, and deleting them.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar crear un Item con el campo name en blanco.

	// Analysis of sentence coverage total: Covered 51.1% 45/88 total instructions
	// Analysis of sentence coverage save(): Covered 95.8% 23/24 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 15/15 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 20.0%

	@Test
	public void CreateEditItemTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:

				{"provider1", "item", "description", "http://www.google.es",
					"http://images5.fanpop.com/image/photos/30400000/Yosh-Yosh-and-Do-wallpapers-yoshi-and-birdo-30426663-1024-768.jpg",
					null },

				// TESTS NEGATIVOS:

				{"provider1", "", "description", "http://www.google.es",
					"http://images5.fanpop.com/image/photos/30400000/Yosh-Yosh-and-Do-wallpapers-yoshi-and-birdo-30426663-1024-768.jpg",
					ConstraintViolationException.class },

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();

			this.CreateEditItemTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(Class<?>) testingData[i][5]);
			this.rollbackTransaction();
		}
	}

	protected void CreateEditItemTemplate(final String username,
			final String name, final String description, final String link,
			final String pictures, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			super.authenticate(username);

			Item item = this.itemService.create();

			item.setName(name);
			item.setDescription(description);
			item.setLink(link);
			item.setPictures(pictures);

			// UPDATING

			this.itemService.save(item);

			this.itemService.flush();

			unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: RF-10.1 An actor who is authenticated as a company must be
	// Manage his or her catalogue of items, which includes listing, showing,
	// creating, up-dating, and deleting them.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar que un Provider borre un Item que no es suyo.
	
	// Analysis of sentence coverage total: Covered 43.2% 38/88 total instructions
	// Analysis of sentence coverage delete(): Covered 100.0% 19/19 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 12/12 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 66.66%

	@Test
	public void deleteItemTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:

				{ "provider1", "item1", null },


				// TESTS NEGATIVOS:
				{ "provider2", "item1", IllegalArgumentException.class },

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.deleteItemTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void deleteItemTemplate(final String username,
			final String itemId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(username);
			this.itemService.delete(this.itemService.findOne(this.getEntityId(itemId)));
			this.itemService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
