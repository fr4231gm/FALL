package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Order;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OrderServiceTest extends AbstractTest {

	@Autowired
	private OrderService orderService;

	// Requirement: RF 22. An actor who is authenticated as a company must be able to:
	// 1. Browse the catalogue of orders and navigate to the profile of the
	// corresponding customer, which includes his or her personal data plus his
	// or her list of orders.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar mostrar una Order estando logeado como un Provider que no existe.
	// Intentar mostrar una Order estando logeado como una Company que no existe.

	// Analysis of sentence coverage total: Covered 7.6% 18/238 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 45.0%

	@Test
	public void displayOrderByCompanyTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "company1", "order1", null },

				// TEST NEGATIVO:
				{ "provider11", "order1", IllegalArgumentException.class },
		
				// TEST NEGATIVO:
				{ "company11", "order1", IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.displayOrderByCompanyTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void displayOrderByCompanyTemplate(final String username,
			final String orderId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.orderService.findOne(this.getEntityId(orderId));

			this.orderService.flush();

			// Hacemos log out para la siguiente iteracion
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 21. An actor who is authenticated as a customer must be
	// able to:
	// 1. Manage an arbitrary number of orders, which includes listing, showing,
	// creating, updating, and deleting them. View the invoice for every order.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar mostrar una Order estando logeado como un Provider que no existe.
	// Intentar mostrar una Order estando logeado como una Company que no existe.

	// Analysis of sentence coverage total: Covered 7.6% 18/238 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 45.0%

	@Test
	public void displayOrderByCustomerTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "customer1", "order1", null },

				// TEST NEGATIVO:
				{ "provider11", "order1", IllegalArgumentException.class },
				
				// TEST NEGATIVO:
				{ "company11", "order1", IllegalArgumentException.class }
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.displayOrderByCustomerTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void displayOrderByCustomerTemplate(final String username,
			final String orderId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.orderService.findOne(this.getEntityId(orderId));

			this.orderService.flush();

			// Hacemos log out para la siguiente iteracion
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 21. An actor who is authenticated as a customer must be
	// able to:
	// 1. Manage an arbitrary number of orders, which includes listing, showing,
	// creating, updating, and deleting them. View the invoice for every order.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar listar Orders estando logeado como un Provider que no existe.
	// Intentar listar Orders estando logeado como una Company que no existe.
	
	// Analysis of sentence coverage total: Covered 5.9% 14/238 total instructions
	// Analysis of sentence coverage findOrdersByCustomerId(): Covered 100.0% 7/7 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 40.0%

	@Test
	public void listOrderByCustomerTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "customer1", null },

				// TEST NEGATIVO:
				{ "provider11", IllegalArgumentException.class },
				
				// TEST NEGATIVO:
				{ "company11", IllegalArgumentException.class }
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listOrderByCustomerTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void listOrderByCustomerTemplate(final String username,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.orderService
					.findOrdersByCustomerId(this.getEntityId(username));

			this.orderService.flush();

			// Hacemos log out para la siguiente iteracion
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 21. An actor who is authenticated as a customer must be able to:
	// 1. Manage an arbitrary number of orders, which includes listing, showing,
	// creating, updating, and deleting them. View the invoice for every order.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar crear un Order con el campo material vacio.
	
	// Analysis of sentence coverage total: Covered 31.9% 76/238 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 32/32 total instructions
	// Analysis of sentence coverage save(): Covered 54.9% 28/51 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 67.5%

	@Test
	public void CreateEditOrderTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:

				{
						"customer1",
						"https://raw.githubusercontent.com/pedroswe/3Dprint/master/8Star_DragonBall_V2.stl",
						"PLA", "comment", null },

				// TESTS NEGATIVOS:

				{ "customer1", "", "PLA", "comment",
						ConstraintViolationException.class },
						
				// TESTS NEGATIVOS:

				{ "customer1", "https://raw.githubusercontent.com/pedroswe/3Dprint/master/8Star_DragonBall_V2.stl",
						"", "comment",
						ConstraintViolationException.class }		

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();

			this.CreateEditOrderTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (Class<?>) testingData[i][4]);
			this.rollbackTransaction();
		}
	}

	protected void CreateEditOrderTemplate(final String username,
			final String stl, final String material, final String comments,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			super.authenticate(username);

			Order order = this.orderService.create();

			order.setStl(stl);
			order.setMaterial(material);
			order.setComments(comments);

			// UPDATING

			this.orderService.save(order);

			this.orderService.flush();

			unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 21. An actor who is authenticated as a customer must be able to:
	// 1. Manage an arbitrary number of orders, which includes listing, showing,
	// creating, updating, and deleting them. View the invoice for every order.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar borrar una Order siendo un Provider.
	// Intentar borrar una Order siendo una Company.

	// Analysis of sentence coverage total: Covered 19.7% 47/238 total instructions
	// Analysis of sentence coverage delete(): Covered 96.7% 29/30 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 45.0%

	@Test
	public void deleteOrderTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:

				{ "customer1", "order0", null },

				// TESTS NEGATIVOS:
				{ "provider2", "order1", IllegalArgumentException.class },
				
				// TESTS NEGATIVOS:
				{ "company1", "order1", IllegalArgumentException.class },

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.deleteOrderTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void deleteOrderTemplate(final String username,
			final String orderId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			
			super.authenticate(username);
			
			this.orderService.delete(this.getEntityId(orderId));
			
			this.orderService.flush();
			
			this.unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
