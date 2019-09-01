
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.SocialProfile;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	@Autowired
	SocialProfileService	socialProfileService;

	@Autowired
	ActorService			actorService;

	@Autowired
	UserAccountService		uaService;


	//Requierement:
	// 23 An actor who is authenticated must be able to:
	// 1. create and edit his social profile

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar crear socialProfile sin Nick

	// Analysis of sentence coverage total(): Covered 53.6% 45/84 total instructions
	// Analysis of sentence coverage save(): Covered 95.5% 21/22 total instructions
	// Analysis of sentence coverage create(): Covered 100.0% 13/13 total instructions
	// Analysis of sentence coverage findByActor(): Covered 100.0% 8/8 total instructions

	// Analysis of data coverage: 45%

	@Test
	public void CreateEditSocialProfileTestDriver() {

		final Object testingData[][] = {

			//TEST POSITIVO: 
			{
				"rookie3", "Nick", "https://www.google.es", "ENDESA", null
			}, //Crear un social profile

			//TESTS NEGATIVOS:
			{
				"rookie3", "", "https://www.google.es", "ENDESA", ConstraintViolationException.class
			}, //Intentar crear un social profile sin nick

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.CreateEditSocialProfileTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			this.rollbackTransaction();
		}

	}

	protected void CreateEditSocialProfileTemplate(final String username, final String nick, final String link, final String socialNetwork, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			//Creamos el Data y le setteamos sus atributos
			final SocialProfile socialProfile = this.socialProfileService.create();
			socialProfile.setNick(nick);
			socialProfile.setLink(link);
			socialProfile.setSocialNetwork(socialNetwork);

			//Lo guardamos
			final SocialProfile saved = this.socialProfileService.save(socialProfile);
			final Actor principal = this.actorService.findByPrincipal();
			//Comprobamos que se ha creado
			Assert.isTrue(this.socialProfileService.findByActor(principal.getId()).contains(saved));

			//Hacemos log out para la siguiente iteraciï¿½n
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	//An actor authenticated as Rookie must be able to:
	// 23 An actor who is authenticated must be able to:
	// 2. delete a socialProfile

	//In the case of negative tests, the business rule that is intended to be broken: 
	//Intentar borrar un socialProfile que no es mio

	// Analysis of sentence coverage total(): Covered 42.9% 36/84 total instructions
	// Analysis of sentence coverage delete(): Covered 100.0% 19/19 total instructions
	// Analysis of sentence coverage findAll(): Covered 100.0% 4/4 total instructions
	// Analysis of sentence coverage findOne(): Covered 100.0% 10/10 total instructions

	// Analysis of data coverage: 100%

	@Test
	public void DeleteSocialProfileTestDriver() {

		final Object testingData[][] = {

			//TEST POSITIVO: 

			{
				"rookie1", "socialProfile9", null
			}, //Borrar mi socialProfile

			//TESTS NEGATIVOS:
			{
				"rookie2", "socialProfile3", IllegalArgumentException.class
			}, //Intentar borrar un socialProfile que no es mio

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.DeleteSocialProfileRedordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void DeleteSocialProfileRedordTemplate(final String username, final String socialProfile, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			//Intentamos borrar el social profile y hacemos flush
			final SocialProfile m = this.socialProfileService.findOne(this.getEntityId(socialProfile));

			this.socialProfileService.delete(m);

			//Comprobamos que se ha borrado
			Assert.isTrue(!this.socialProfileService.findAll().contains(m));

			//Hacemos log out para la siguiente iteración
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
