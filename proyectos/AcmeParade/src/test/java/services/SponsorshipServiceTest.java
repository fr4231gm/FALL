
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

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ParadeService		paradeService;

	//Requierement: RF-16.1 An actor who is authenticated as a sponsor must be able to:
	// Manage his or her sponsorships, which includes listing,showing,creating,updating,and removing them. Note that
	// removing a sponsorship does not actually delete it from the system, but de-activates it. A de-activated sponsorship can be
	// re-activated later.

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Listar sponsors siendo un chapter
		
	//Analysis of sentence coverage: Covered 100%
																
	//Analysis of data coverage: Covered 52 /52 total instructions

	@Test
	public void listSponsorshipTestDriver() {

		final Object testingData[][] = {
			//Test Positivo:
			{
				"sponsor1", null
			},
			//Test Negativo
			{
				"chapter1", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listSponsorshipsTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	@Test
	public void createSponsorshipTestDrive() {
		final Object testingData[][] = {
			//Test Positivo: 
			{
				"sponsor1", this.getEntityId("creditCard1"), this.getEntityId("parade1"), "https://www.Pruebaedditstatic.com/new-icon.png", "https://www.reddit.com/", null,
			},

			//Test Negativo:
			{
				"sponsor1", this.getEntityId("parade1"), this.getEntityId("parade1"), "https://www.Pruebaedditstatic.com/new-icon.png", "https://www.reddit.com/", IllegalArgumentException.class
			}

		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.createSponsorshipTemplate((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
		this.rollbackTransaction();
	}
	
	//Requierement: 16. An actor who is authenticated as a sponsor must be able to:
	//1. Manage his or her sponsorships, which includes listing, showing, creating, updating, and removing them

	//In the case of negative tests, the business rule that is intended to be broken: 
	//intento editar un sponsorship siendo un Brotherhood
	//intento editar un sponsorship con url inválida
			
	//Analysis of sentence coverage: Covered 100%
																	
	//Analysis of data coverage: Covered 157 /157 total instructions
	
	@Test
	public void editSponsorshipTestDrive() {
		final Object testingData[][] = {

			//Test Positivo: 
			{
				"sponsor1", super.getEntityId("sponsorship1"), super.getEntityId("creditCard1"), "https://www.Banner.es", "http://www.Banner.es", null
			},

			// Test negativo: intento editar un sponsorship siendo un Brotherhood
			{
				"brotherhood1", super.getEntityId("sponsorship1"), super.getEntityId("creditCard1"), "https://www.Banner.es", "http://www.Banner.es", IllegalArgumentException.class
			},

			// Test negativo: intento editar un sponsorship con url inválida
			{
				"sponsor1", super.getEntityId("sponsorship1"), super.getEntityId("creditCard1"), "Banner", "http://www.Banner.es", ConstraintViolationException.class
			}

		};
		this.startTransaction();
		for (int i = 0; i < testingData.length; i++)
			this.editSponsorshipTemplate((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
		this.rollbackTransaction();

	}
	
	//Requierement: 16. An actor who is authenticated as a sponsor must be able to:
	//1. Manage his or her sponsorships, which includes listing, showing, creating, updating, and removing them

	//In the case of negative tests, the business rule that is intended to be broken: 
	//intento borrar un sponsorship que no me pertenece
	//intento borrar un sponsorship sin ser un sponsor
				
	//Analysis of sentence coverage: Covered 100%
																		
	//Analysis of data coverage: Covered 93 /93 total instructions
	
	@Test
	public void deleteSponsorshipTestDrive() {
		final Object testingData[][] = {
			//Test Positivo:
			{
				"sponsor1", this.getEntityId("sponsorship1"), null
			},
			//Test Negativo: intento borrar un sponsorship que no me pertenece
			{
				"sponsor2", this.getEntityId("sponsorship1"), IllegalArgumentException.class
			},
			//Test Negativo: intento borrar un sponsorship sin ser un sponsor
			{
				"chapter2", this.getEntityId("sponsorship1"), IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.deleteSponsorshipTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}
	//Templates ----------------------------------------------------------------------------------------------------------------
	protected void listSponsorshipsTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);
			final int aux = this.getEntityId(username);

			this.sponsorshipService.findSponsorshipsBySponsorId(aux);
			this.sponsorshipService.flush();

			//Hacemos log out para la siguiente iteración
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void createSponsorshipTemplate(final String username, final int creditcardId, final int paradeId, final String banner, final String targetURL, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final Sponsorship sponsorship = this.sponsorshipService.create();

			sponsorship.setCreditCard(this.creditCardService.findOne(creditcardId));
			sponsorship.setParade(this.paradeService.findOne(paradeId));
			sponsorship.setBanner(banner);
			sponsorship.setTargetURL(targetURL);

			this.sponsorshipService.save(sponsorship);
			this.sponsorshipService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void editSponsorshipTemplate(final String username, final int sponsorshipId, final int creditcardId, final String banner, final String targetURL, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);

			sponsorship.setCreditCard(this.creditCardService.findOne(creditcardId));
			sponsorship.setBanner(banner);
			sponsorship.setTargetURL(targetURL);

			this.sponsorshipService.save(sponsorship);
			this.sponsorshipService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void deleteSponsorshipTemplate(final String username, final int sponsorshipId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(username);
			this.sponsorshipService.delete(sponsorshipId);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
