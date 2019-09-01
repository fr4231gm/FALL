package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Sponsorship;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private SponsorshipService sponsorshipService;

	@Autowired
	private PostService postService;

	// Requirement: 23.1 
	// List Sponsorship as Provider
	// Analysis of sentence coverage total: Covered 4.3% 21/490 total instructions
	// Analysis of sentence coverage findSponsorshipByProviderId(): Covered 100% 14/14 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
	
	// Analysis of data coverage: 100%

	@Test
	public void listSponsorshipProviderTestDriver() {

		final Object testingData[][] = {
				// Test Positivo:
				{ "provider1", null },
				// Test Negativo
				{ "company1", IllegalArgumentException.class },
				// Test Negativo
				{ "designer1", IllegalArgumentException.class },
				// Test Negativo
				{ null, IllegalArgumentException.class }

		};
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listSponsorshipsProviderTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void listSponsorshipsProviderTemplate(final String username,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(username);

			this.sponsorshipService.findSponsorshipsByProviderId();
			this.sponsorshipService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requirement: 20.2
	// List Sponsorship as Designer
	// Analysis of sentence coverage total: Covered 4.3% 21/490 total instructions
	// Analysis of sentence coverage findSponsorshipByDesignerId(): Covered 100% 14/14 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
		
	// Analysis of data coverage: 100%

	@Test
	public void listSponsorshipDesignerTestDriver() {

		final Object testingData[][] = {
				// Test Positivo:
				{ "designer1", null },
				// Test Negativo
				{ "company1", IllegalArgumentException.class },
				// Test Negativo
				{ null, IllegalArgumentException.class }

		};
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listSponsorshipsDesignerTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void listSponsorshipsDesignerTemplate(final String username,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(username);

			this.sponsorshipService.findSponsorshipsByDesignerId();
			this.sponsorshipService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requirement: 23.1
	// Create a sponsorship.
	// Analysis of sentence coverage total: Covered 24.3% 119/490 total instructions
	// Analysis of sentence coverage save(): Covered 75% 51/68 total instructions
	// Analysis of sentence coverage checkCreditCard(): Covered 82.9% 29/35 total instructions
	// Analysis of sentence coverage create(): Covered 100% 32/32 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
	
	// Analysis of data coverage: 54.13%
	
	@Test
	public void createSponsorshipTestDrive() {
		final Object testingData[][] = {
			//Test Positivo: 
			{"provider1", this.getEntityId("post3"), "https://www.Pruebaedditstatic.com/new-icon.png", "https://www.reddit.com/", null},

			//Test Negativo:
			{"provider1", this.getEntityId("post2"), "https://www.Pruebaedditstatic.com/new-icon.png", "https://www.reddit.com/", IllegalArgumentException.class },

			//Test Negativo:
			{"provider1", this.getEntityId("post3"), null, "https://www.reddit.com/", ConstraintViolationException.class }
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.createSponsorshipTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		this.rollbackTransaction();
	}
	
	protected void createSponsorshipTemplate(final String username,  final int postId, final String banner, final String targetPage, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final Sponsorship sponsorship = this.sponsorshipService.create();

			sponsorship.setBanner(banner);
			sponsorship.setTargetPage(targetPage);
			sponsorship.setPost(this.postService.findOne(postId));

			this.sponsorshipService.save(sponsorship);
			this.sponsorshipService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	// Requirement: 23.1
	// Edit a sponsorship.
	// Analysis of sentence coverage total: Covered 21.4% 105/490 total instructions
	// Analysis of sentence coverage save(): Covered 85.3% 58/68 total instructions
	// Analysis of sentence coverage checkCreditCard(): Covered 82.9% 29/35 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions
	// Analysis of sentence coverage flush(): Covered 100% 4/4 total instructions
	
	// Analysis of data coverage: 50%
	
	@Test
	public void editSponsorshipTestDrive() {
		final Object testingData[][] = {
			//Test Positivo: 
			{"provider1", this.getEntityId("sponsorship1"), "https://www.Pruebaedditstatic.com/new-icon.png", "https://www.reddit.com/", null},

			//Test Negativo:
			{"provider1", this.getEntityId("sponsorship2"), "https://www.Pruebaedditstatic.com/new-icon.png", "https://www.reddit.com/", IllegalArgumentException.class},

			//Test Negativo:
			{"provider1", this.getEntityId("sponsorship1"), null, "https://www.reddit.com/",ConstraintViolationException.class}
		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.editSponsorshipTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		this.rollbackTransaction();
	}
	
	protected void editSponsorshipTemplate(final String username,  final int sponsorshipId, final String banner, final String targetPage, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);

			sponsorship.setBanner(banner);
			sponsorship.setTargetPage(targetPage);

			this.sponsorshipService.save(sponsorship);
			this.sponsorshipService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Requirement: 23.1
	// Activate sponsorship
	// Analysis of sentence coverage total: Covered 10.8% 53/490 total instructions
	// Analysis of sentence coverage activate(): Covered 100% 39/39 total instructions
	// Analysis of sentence coverage findOne(): Covered 100% 11/11 total instructions
	
	// Analysis of data coverage: 75%
	
	@Test
	public void activateSponsorshipTestDrive() {
		final Object testingData[][] = {
			//Test Positivo:
			{"provider4", this.getEntityId("sponsorship4"), null},
			//Test Negativo: 
			{"provider1", this.getEntityId("sponsorship1"), IllegalArgumentException.class},
			//Test Negativo: 
			{"provider1", this.getEntityId("sponsorship4"), IllegalArgumentException.class},
			//Test Negativo: 
			{"designer1", this.getEntityId("sponsorship4"), IllegalArgumentException.class},
			//Test Negativo: 
			{null, this.getEntityId("sponsorship4"), IllegalArgumentException.class}
			
		};
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.activateSponsorshipTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}
	
	protected void activateSponsorshipTemplate(final String username, final int sponsorshipId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(username);
			this.sponsorshipService.activate(sponsorshipId);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Requirement: 23.1
	// Desactive sponsorship
	// Analysis of sentence coverage total: Covered 10% 49/490 total instructions
	// Analysis of sentence coverage desactivate: Covered 10% 35/35 total instructions
	// Analysis of sentence coverage findOne: Covered 10% 11/11 total instructions
	
	// Analysis of data coverage: 75%
	
	@Test
	public void desactivateSponsorshipTestDrive() {
		final Object testingData[][] = {
			//Test Positivo:
			{"provider1", this.getEntityId("sponsorship1"), null},
			//Test Negativo: 
			{"provider1", this.getEntityId("sponsorship2"), IllegalArgumentException.class},
			//Test Negativo: 
			{null, this.getEntityId("sponsorship1"), IllegalArgumentException.class},
			//Test Negativo: 
			{"designer1", this.getEntityId("sponsorship1"), IllegalArgumentException.class}
		};
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.desactivateSponsorshipTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}
	
	protected void desactivateSponsorshipTemplate(final String username, final int sponsorshipId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(username);
			this.sponsorshipService.desactivate(sponsorshipId);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
