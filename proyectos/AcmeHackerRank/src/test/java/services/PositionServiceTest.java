
package services;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Position;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionServiceTest extends AbstractTest {

	@Autowired
	private PositionService	positionService;


	//Requierement: 7.4 An actor who is not authenticated as a hacker must be able to:
	//	Search for a position using a single key word that must be contained in its title, its
	//	description, its profile, its skills, its technologies, or the name of the corresponding		
	//	company
	//In the case of negative tests, the business rule that is intended to be broken:
	//No procede

	// Analysis of sentence coverage total: Covered 3.4% 14/412 total instructions
	// Analysis of sentence coverage searchPositionAnonymous(): Covered 100% 7/7 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
			
	// Analysis of data coverage: 100%

	@Test
	public void positionsSearchTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"junior", null
			}
		// Listar las positions
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.positionSearchTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void positionSearchTemplate(final String keyword, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			this.positionService.searchPositionAnonymous(keyword);

			this.positionService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 7. An actor who is not authenticated must be able to:
	// 2. List the positions available and navigate to the corresponding
	// companies.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// No procede.

	// Analysis of sentence coverage total: Covered 3.2% 13/412 total instructions
	// Analysis of sentence coverage findPositionsNotCancelledFinalMode(): Covered 100% 6/6 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
				
	// Analysis of data coverage: 100%

	@Test
	public void listPositionsTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				null
			}
		// Listar las positions
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listPositionsTemplate((Class<?>) testingData[i][0]);
			this.rollbackTransaction();
		}
	}

	protected void listPositionsTemplate(final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			this.positionService.findPositionsNotCancelledFinalMode();

			this.positionService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: RF 8. An actor who is authenticated must be able to:
	// 1. Do the same as an actor who is not authenticated, but register to the
	// system.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// No procede.

	// Analysis of sentence coverage total: Covered 3.2% 13/412 total instructions
	// Analysis of sentence coverage findPositionsNotCancelledFinalMode(): Covered 100% 6/6 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
					
	// Analysis of data coverage: 100%

	@Test
	public void listPositionsByActorTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"company1", null
			}, // Listar las positions

			// TEST POSITIVO:
			{
				"hacker1", null
			}, // Listar las positions

			// TEST POSITIVO:
			{
				"admin", null
			}
		// Listar las positions
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listPositionsByActorTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void listPositionsByActorTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			super.authenticate(username);

			this.positionService.findPositionsNotCancelledFinalMode();

			this.positionService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: RF-9.1 An actor who is authenticated as a company must be
	// able to:
	// Manage their positions, which includes showing them.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// No procede

	// Analysis of sentence coverage total: Covered 4.4% 18/412 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
						
	// Analysis of data coverage: 100%

	@Test
	public void displayPositionTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"company1", "position1", null
			}
		// Mostrar un segmento
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.displayPositionTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void displayPositionTemplate(final String username, final String positionId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.positionService.findOne(this.getEntityId(positionId));

			this.positionService.flush();

			// Hacemos log out para la siguiente iteracion
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: RF-9.1 An actor who is authenticated as a company must be
	// able to:
	// Manage their positions, which includes creating and editing them.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Intentar crear una Position siendo un Hacker
	// Intentar crear una Position siendo un Administrador
	// Intentar crear una Position con el campo Profile vacio
	// Intentar crear una Position con el Salary negativo
	// Intentar crear una Position con el campo Title vacio

	// Analysis of sentence coverage total: Covered 29.6% 122/412 total instructions
	// Analysis of sentence coverage save(): Covered 82.8% 53/64 total instructions
	// Analysis of sentence coverage generateTicker(): Covered 100% 28/28 total instructions
	// Analysis of sentence coverage create(): Covered 100% 34/34 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
							
	// Analysis of data coverage: 78.6%


	@SuppressWarnings("deprecation")
	@Test
	public void CreateEditPositionTestDriver() {
		
		final Date deadline;
		
		deadline = new Date();
		deadline.setYear(2020);
		deadline.setMonth(2);
		deadline.setDate(25);

		final Object testingData[][] = {

				// TEST POSITIVO:
				
				{ "company1", "position", "description", deadline, "profile1", "skills",
					"technologies", 18000.0, null },
					
				{ "company2", "position", "description", deadline, "profile2", "skills",
					"technologies", 1000.0, null },
				
				{ "company3", "position", "description", deadline, "profile3", "skills",
					"technologies", 20000.0, null },	
	
				{ "company4", "position", "description", deadline, "profile4", "skills",
					"technologies", 18000.0, null },
					
				{ "company5", "position", "description", deadline, "profile5", "skills",
					"technologies", 18000.0, null },
					
				// TESTS NEGATIVOS:
					
				{ "hacker1", "position", "description", deadline, "profile", "skills",
					"technologies", 18000.0, IllegalArgumentException.class }, 
															
				{ "admin1", "position", "description", deadline, "profile", "skills",
					"technologies", 18000.0, IllegalArgumentException.class },  
					
				{ "company1", "position", "description", deadline, "", "skills",
					"technologies", 18000.0, ConstraintViolationException.class },  	

				{ "company1", "position", "description", deadline, "profile", "skills",
					"technologies", -18000.0, ConstraintViolationException.class },
															

				{ "company1", "", "description", deadline, "profile", "skills",
					"technologies", 18000.0, ConstraintViolationException.class },
															
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();

			this.CreateEditPositionTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Date) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5],(String) testingData[i][6],
					(Double) testingData[i][7], (Class<?>) testingData[i][8]);
			this.rollbackTransaction();
		}
	}

	protected void CreateEditPositionTemplate(final String username,
			final String title, final String description,
			final Date deadline, final String profile,
			final String skills, final String technologies,
			final Double salary, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			
			super.authenticate(username);
			
			Position position = this.positionService.create();
			
			position.setTitle(title);
			position.setDescription(description);
			position.setDeadline(deadline);
			position.setProfile(profile);
			position.setSkills(skills);
			position.setTechnologies(technologies);
			position.setSalary(salary);

			// UPDATING
			Position saved = this.positionService.save(position);

			this.positionService.save(saved);
			
			this.positionService.flush();
			
			unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requierement: RF-9.1 An actor who is authenticated as a company must be
	// able to:
	// Manage their positions, which includes deleting them.

	// In the case of negative tests, the business rule that is intended to be
	// broken:
	// Intentar borrar una Position que no es de una Company
	// Intentar borrar una Position como Administrator
	// Intentar borrar una Position como Hacker

	// Analysis of sentence coverage total: Covered 11.2% 46/412 total instructions
		// Analysis of sentence coverage delete(): Covered 100% 28/28 total instructions
		// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions
		// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
								
		// Analysis of data coverage: 100%

	@Test
	public void deletePositionTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:

				{ "company1", "position1", null },

				{ "company1", "position2", null },

				{ "company1", "position3", null },

				{ "company2", "position7", null },

				{ "company2", "position8", null },

				// TESTS NEGATIVOS:
				{ "company2", "position1", IllegalArgumentException.class },

				{ "admin1", "position1", IllegalArgumentException.class },

				{ "hacker1", "position1", IllegalArgumentException.class },

				{ "admin", "position2", IllegalArgumentException.class },

				{ "company3", "position1", IllegalArgumentException.class }

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.deletePositionTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void deletePositionTemplate(final String username, final String positionId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(username);
			this.positionService.delete(this.positionService.findOne(this.getEntityId(positionId)));
			this.positionService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
