package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Parade;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ParadeServiceTest extends AbstractTest {

	@Autowired
	private ParadeService paradeService;
	
	@Autowired
	private BrotherhoodService brotherhoodService;
	
	//Requierement: RF-3.1 An actor who is authenticated as a brotherhood must be able to:
	// List their parades grouped by their status

	//In the case of negative tests, the business rule that is intended to be broken: No procede
				
	//Analysis of sentence coverage: Covered 83.3%
										
	//Analysis of data coverage: Covered 20 / 24 total instructions

	@Test
	public void listParadesTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ "brotherhood1", null }, // Listar las parades

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listParadesTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void listParadesTemplate(final String username,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.paradeService.findAll();

			this.paradeService.flush();

			// Hacemos log out para la siguiente iteraci�n
			unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	//Requierement: RF-3.2 An actor who is authenticated as a brotherhood must be able to:
	// Make a copy of one of their parades

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Copiar una Parade siendo un admin
					
	//Analysis of sentence coverage: Covered 100%
											
	//Analysis of data coverage: Covered 66 / 66 total instructions

	@Test
	public void copyParadeTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "brotherhood1", "parade1", null }, // Copiar una Parade

				// TEST NEGATIVO:
				{ "administrator1", "parade1", IllegalArgumentException.class } // Copiar
																				// una
																				// Parade
																				// siendo
																				// un
																				// admin

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.copyParadeTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void copyParadeTemplate(final String username,
			final String paradeId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.paradeService.copy(this.paradeService.findOne(this
					.getEntityId(paradeId)));

			this.paradeService.flush();

			// Hacemos log out para la siguiente iteraci�n
			unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	//Requierement: RF-14.1 An actor who is not authenticated must be able to:
	// navigate to the brotherhoods that have settle in those areas

	//In the case of negative tests, the business rule that is intended to be broken: No procede 
						
	//Analysis of sentence coverage: Covered 100%
												
	//Analysis of data coverage: Covered 16 / 20 total instructions

	@Test
	public void listParadesByChaptersAreaTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ super.getEntityId("brotherhood1"), null } // Muestra los brotherhood
		// correctamente

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listParadesByChaptersAreaTemplate((int) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void listParadesByChaptersAreaTemplate(final int brotherhoodId,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			this.paradeService.findParadesByBrotherhoodId(brotherhoodId);

			this.paradeService.flush();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	//Requierement: RF-2.2 An actor who is authenticated as a chapter must be able to:
	// Manage the parades that are published by the brotherhoods in the area that they co-ordinate
	//making decisions on the parades that have status submitted

	//In the case of negative tests, the business rule that is intended to be broken: 
	// Intenta cambiar el status de una Parade a rejected sin incluir un rejectReason

							
	//Analysis of sentence coverage: Covered 100%
													
	//Analysis of data coverage: Covered 33 / 33 total instructions

	@Test
	public void decideParadeTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "chapter1", super.getEntityId("parade2"), "ACCEPTED", "",
						null }, // Cambia el status de la Parade correctamente

				// TEST NEGATIVO:
				{ "chapter1", super.getEntityId("parade2"), "REJECTED", "",
						IllegalArgumentException.class } // Intenta cambiar el
															// status de una
															// Parade
															// a rejected sin
															// incluir
															// un rejectReason

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.decideParadeTemplate((String) testingData[i][0],
					(int) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (Class<?>) testingData[i][4]);
			this.rollbackTransaction();
		}
	}

	protected void decideParadeTemplate(final String username,
			final int paradeId, final String status, final String rejectReason,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			super.authenticate(username);

			Parade p = this.paradeService.findOne(paradeId);

			p.setStatus(status);

			this.paradeService.save(p);

			this.paradeService.flush();

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	//Requierement: RF-2. An actor who is authenticated as a chapter must be able to:
	// 2. Manage the parades that are published by the brotherhoods in the area
	// that they co-ordinate.

	//In the case of negative tests, the business rule that is intended to be broken: 
	// Intenta cambiar el status de una Parade a rejected sin incluir un rejectReason

								
	//Analysis of sentence coverage: Covered 100%
														
	//Analysis of data coverage: Covered 60 / 60 total instructions

	@Test
	public void listParadesByBrotherhoodByAreaTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ "chapter1", super.getEntityId("area1"), super.getEntityId("brotherhood1"), null }, // Cambia el status de la Parade correctamente
																			
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listParadesByBrotherhoodByAreaTemplate((String) testingData[i][0],
					(int) testingData[i][1], (int) testingData[i][2],
					(Class<?>) testingData[i][3]);
			this.rollbackTransaction();
		}
	}

	protected void listParadesByBrotherhoodByAreaTemplate(final String username,
			final int areaId, final int brotherhoodId,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			super.authenticate(username);
			
			this.brotherhoodService.findBrotherhoodsByArea(areaId);

			this.paradeService.findParadesByBrotherhoodId(brotherhoodId);

			this.paradeService.flush();

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
