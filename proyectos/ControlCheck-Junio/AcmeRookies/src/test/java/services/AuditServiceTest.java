package services;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Audit;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditServiceTest extends AbstractTest {

	@Autowired
	private AuditService auditService;

	// Requierement: 3.2 An actor who is authenticated as an auditor must be able to:
	// Manage his or her audits, which includes listing them, showing them,
	// creating them, updating, and deleting them.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Un Auditor intenta listar un Audit que no es suyo.

	// Analysis of sentence coverage total: Covered 6.8% 12/177 total instructions
	// Analysis of sentence coverage findAuditsByAuditorId(): Covered 100.0% 5/5 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 8.3%

	@Test
	public void listAuditTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ "auditor1", super.getEntityId("auditor1"), null }, 
		
		// TEST NEGATIVO:
		{ "auditor18", super.getEntityId("auditor2"), IllegalArgumentException.class }
		
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listAuditTemplate((String) testingData[i][0],
					(int) testingData[i][1],(Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void listAuditTemplate(final String username,
			final int auditorId ,final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.auditService.findAuditsByAuditorId(auditorId);

			this.auditService.flush();

			// Hacemos log out para la siguiente iteracion
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	// Requierement: 3.2 An actor who is authenticated as an auditor must be able to:
	// Manage his or her audits, which includes listing them, showing them,
	// creating them, updating, and deleting them.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Un Auditor intenta mostrar un Audit que no es suyo.

	// Analysis of sentence coverage total: Covered 10.2% 18/177 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 8.3%
	
	@Test
	public void displayAuditTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ "auditor1", "audit1", null },
		
		// TEST POSITIVO:
		{ "auditor18", "audit2", IllegalArgumentException.class } 
		
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.displayAuditTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void displayAuditTemplate(final String username,
			final String auditId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.auditService.findOne(this.getEntityId(auditId));

			this.auditService.flush();

			// Hacemos log out para la siguiente iteracion
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	// Requierement: 3.2 An actor who is authenticated as an auditor must be able to:
	// Manage his or her audits, which includes listing them, showing them,
	// creating them, updating, and deleting them.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar crear un Audit con el campo text en blanco.

	// Analysis of sentence coverage total: Covered 43.5% 77/177 total instructions
	// Analysis of sentence coverage save(): Covered 97.7% 43/44 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 27/27 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 25.0%
	
	@SuppressWarnings("deprecation")
	@Test
	public void CreateEditAuditTestDriver() {
		
		final Date moment;
		
		moment = new Date();
		moment.setYear(2018);
		moment.setMonth(2);
		moment.setDate(25);

		final Object testingData[][] = {

				// TEST POSITIVO:

				{"auditor1", moment, "text",
					4.9, null },

				// TESTS NEGATIVOS:

				{"auditor1", moment, "",
					3.1, ConstraintViolationException.class },

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();

			this.CreateEditAuditTemplate((String) testingData[i][0],
					(Date) testingData[i][1], (String) testingData[i][2],
					(Double) testingData[i][3], (Class<?>) testingData[i][4]);
			this.rollbackTransaction();
		}
	}

	protected void CreateEditAuditTemplate(final String username,
			final Date moment, final String text, final Double score,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			super.authenticate(username);

			Audit audit = this.auditService.create();

			audit.setMoment(moment);
			audit.setText(text);
			audit.setScore(score);

			// UPDATING
			Audit saved = this.auditService.save(audit);

			this.auditService.save(saved);

			this.auditService.flush();

			unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	// Requierement: 3.2 An actor who is authenticated as an auditor must be able to:
	// Manage his or her audits, which includes listing them, showing them,
	// creating them, updating, and deleting them.

	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar borrar un audit que no este en modo borrador.

	// Analysis of sentence coverage total: Covered 26.0% 45/173 total instructions
	// Analysis of sentence coverage delete(): Covered 96.4% 27/28 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 35.0%
	
	@Test
	public void deleteAuditTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:

				{ "auditor1", "audit3", null },


				// TESTS NEGATIVOS:
				{ "auditor1", "audit2", IllegalArgumentException.class },

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.deleteAuditTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void deleteAuditTemplate(final String username,
			final String auditId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(username);
			
			this.auditService.delete(this.auditService.findOne(this.getEntityId(auditId)));
			
			this.auditService.flush();
			
			this.unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
