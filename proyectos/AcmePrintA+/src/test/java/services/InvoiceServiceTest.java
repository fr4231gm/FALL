
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Actor;
import domain.Application;
import domain.Invoice;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class InvoiceServiceTest extends AbstractTest {

	// Services ---------------------------------------------------------------
	@Autowired
	private InvoiceService		invoiceService;
	@Autowired
	private ActorService		actorService;

	@Autowired
	private ApplicationService	applicationService;


	// Requirement: RF 21. An actor who is authenticated as a customer must be able to:
	// 1. Manage an arbitrary number of orders, which includes listing, showing,
	// creating, updating, and deleting them. View the invoice for every order.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Mostrar un invoice estando logeado como un Provider que no existe.
	// Mostrar un invoice estando logeado como una Company que no existe.

	// Analysis of sentence coverage total: Covered 11.6% 14/121 total instructions
	// Analysis of sentence coverage findInvoiceByOrderId(): Covered 100.0% 7/7 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 45.0%

	@Test
	public void displayInvoiceByOrderTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"company1", "order1", null
			},

			// TEST NEGATIVO:
			{
				"provider11", "order1", IllegalArgumentException.class
			},

			// TEST NEGATIVO:
			{
				"company11", "order1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.displayInvoiceByOrderTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void displayInvoiceByOrderTemplate(final String username, final String orderId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.invoiceService.findInvoiceByOrderId(this.getEntityId(orderId));

			this.invoiceService.flush();

			// Hacemos log out para la siguiente iteracion
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requirement: 21. An actor who is authenticated as a customer must be able to:
	//1. Manage an arbitrary number of orders, which includes listing, showing, creating, updating, and deleting them. View the invoice for every order.

	//In the case of negative tests, the business rule that is intended to be broken:
	//An invoice is trying to generate when the logged user is a designer

	// Analysis of sentence coverage total: Covered 51.2% 62/121 total instructions
	// Analysis of sentence coverage create(): Covered 100% 40/40 total instructions
	// Analysis of sentence coverage calculatePrice(): Covered 100% 19/19 instructions

	// Analysis of data coverage: 50%

	//create method in the service

	@Test
	public void invoiceCreateTestDriver() {

		final Object testingData[][] = {

			//TEST NEGATIVO:
			{
				"designer1", IllegalArgumentException.class
			},

			//TEST POSITIVO:
			{
				"customer4", null
			},

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.invoiceCreateTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	//Templates---------------------------------------------------------------------------------

	protected void invoiceCreateTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			Application a3 = new Application();
			final Actor n = this.actorService.findByPrincipal();
			final Collection<Application> a = this.applicationService.findApplicationsByCustomerId(n.getId());
			for (final Application a2 : a)
				if (a2.getStatus().equals("ACCEPTED"))
					a3 = a2;

			@SuppressWarnings("unused")
			final Invoice i = this.invoiceService.create(a3.getId());

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//Same requirement

	// Analysis of sentence coverage total: Covered 59.5% 72/121 total instructions
	// Analysis of sentence coverage create(): Covered 100% 40/40 total instructions
	// Analysis of sentence coverage calculatePrice(): Covered 100% 19/19 instructions
	// Analysis of sentence coverage save(): Covered 100% 10/10 instructions

	//Save method in the service

	@Test
	public void invoiceSaveTestDriver() {

		final Object testingData[][] = {

			//TEST NEGATIVO:
			{
				"designer1", IllegalArgumentException.class
			}, //Editar configuracion siendo admin

			//TEST POSITIVO:
			{
				"customer4", null
			}, //Editar configuracion siendo brotherhood

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.invoiceSaveTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	//Templates---------------------------------------------------------------------------------

	protected void invoiceSaveTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			Application a3 = new Application();
			final Actor n = this.actorService.findByPrincipal();
			final Collection<Application> a = this.applicationService.findApplicationsByCustomerId(n.getId());
			for (final Application a2 : a)
				if (a2.getStatus().equals("ACCEPTED"))
					a3 = a2;

			final Invoice i = this.invoiceService.create(a3.getId());
			this.invoiceService.save(i);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
