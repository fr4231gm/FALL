package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Sponsorship;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired 
	private PositionService positionService;

	// Requierement: RF 13.1 An actor who is authenticated as a provider must be able to:
	// Manage his or her sponsorships, which includes listing, showing, creating, updating, and deleting them.
	
	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar listar los sponsorships estando logeado como un rookie.
	
	// Analysis of sentence coverage total: Covered 7.5% 21/279 total instructions
	// Analysis of sentence coverage findSponsorshipsByProviderId(): Covered 100.0% 14/14 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 20.0%
	
	@Test
	public void listSponsorshipTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ "provider1", null }, // Listar items
		
		// TEST NEGATIVO:
		{ "rookie1", IllegalArgumentException.class}
		
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listSponsorshipTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void listSponsorshipTemplate(final String username,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {

			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.sponsorshipService.findSponsorshipsByProviderId();
			
			this.sponsorshipService.flush();

			// Hacemos log out para la siguiente iteracion
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	// Requierement: RF 13.1 An actor who is authenticated as a provider must be able to:
	// Manage his or her sponsorships, which includes listing, showing, creating, updating, and deleting them.
		
	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar listar los sponsorships estando logeado como una company que no existe.
		
	// Analysis of sentence coverage total: Covered 6.5% 18/279 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 35.0%
	
	@Test
	public void displaySponsorshipTestDriver() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ "provider1", "sponsorship1", null },
		
		// TEST NEGATIVO:
		{ "company12", "sponsorship1", IllegalArgumentException.class }
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.displaySponsorshipTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void displaySponsorshipTemplate(final String username,
			final String sponsorshipId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			// Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.sponsorshipService.findOne(this.getEntityId(sponsorshipId));

			this.sponsorshipService.flush();

			// Hacemos log out para la siguiente iteracion
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	// Requierement: RF 13.1 An actor who is authenticated as a provider must be able to:
	// Manage his or her sponsorships, which includes listing, showing, creating, updating, and deleting them.
			
	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar crear o editar un Sponsorship con el campo TargetPage vacio.
			
	// Analysis of sentence coverage total: Covered 23.2% 95/410 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 26/26 total instructions
	// Analysis of sentence coverage save(): Covered 67.3% 33/49 total instructions
	// Analysis of sentence coverage checkCreditCard(): Covered 82.9% 29/35 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 38.25%
	
	@Test
	public void CreateEditSponsorshipTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:

				{"provider1", "http://www.blog.juliopari.com/wp-content/uploads/2011/04/frontbanner960_2.jpg",
					"http://www.opera.es", "position1", null },

				// TESTS NEGATIVOS:

				{"provider1", "http://www.blog.juliopari.com/wp-content/uploads/2011/04/frontbanner960_2.jpg",
					"", "position1", ConstraintViolationException.class },

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();

			this.CreateEditSponsorshipTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (Class<?>) testingData[i][4]);
			this.rollbackTransaction();
		}
	}

	protected void CreateEditSponsorshipTemplate(final String username,
			final String banner, final String targetPage,
			final String positionId ,final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			super.authenticate(username);

			Sponsorship sponsorship = this.sponsorshipService.create();

			sponsorship.setBanner(banner);
			sponsorship.setTargetPage(targetPage);
			sponsorship.setPosition(this.positionService.findOne(this.getEntityId(positionId)));

			// UPDATING

			this.sponsorshipService.save(sponsorship);

			this.sponsorshipService.flush();

			unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	// Requierement: RF 13.1 An actor who is authenticated as a provider must be able to:
	// Manage his or her sponsorships, which includes listing, showing, creating, updating, and deleting them.
				
	// In the case of negative tests, the business rule that is intended to be broken:
	// Intentar que un provider borre un sponsorship que no es suyo.
				
	// Analysis of sentence coverage total: Covered 10.7% 44/410 total instructions
	// Analysis of sentence coverage delete(): Covered 100.0% 26/26 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100.0% 4/4 total instructions

	// Analysis of data coverage: 66.66%
	
	@Test
	public void deleteSponsorshipTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:

				{ "provider2", "sponsorship4", null },


				// TESTS NEGATIVOS:
				{ "provider1", "sponsorship4", IllegalArgumentException.class },

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.deleteSponsorshipTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void deleteSponsorshipTemplate(final String username,
			final String sponsorshipId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(username);
			
			this.sponsorshipService.delete(this.getEntityId(sponsorshipId));
			
			this.sponsorshipService.flush();
			
			this.unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
}
