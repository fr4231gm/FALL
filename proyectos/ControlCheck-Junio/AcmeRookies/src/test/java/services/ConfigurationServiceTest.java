
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Configuration;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	@Autowired
	private ConfigurationService	configurationService;


	//Requierement: this is there is no direct requirement that identifies the configuration but it is intuited in the requirements
	//	that the administrators have one because they can modify diverse properties of the system.
	//An actor who is authenticated as a administrator must be able to:
	//1. Manage the configuration of the system

	//In the case of negative tests, the business rule that is intended to be broken:
	//Try to modify the configuration of the system as a rookie

	// Analysis of sentence coverage total: Covered 48.4% 44/91 total instructions
	// Analysis of sentence coverage findConfiguration(): Covered 81.2% 26/32 total instructions
	// Analysis of sentence coverage save(): Covered 100% 15/15 total instructions
	
	// Analysis of data coverage: 100%

	@Test
	public void editConfigurationTestDriver() {

		final Object testingData[][] = {

			//TEST POSITIVO:
			{
				"admin1", null
			}, //Editar configuracion siendo admin

			//TEST NEGATIVO:
			{
				"rookie1", IllegalArgumentException.class
			}, //Editar configuracion siendo brotherhood

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.editConfigurationTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	//Templates---------------------------------------------------------------------------------

	protected void editConfigurationTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			final Configuration c = this.configurationService.findConfiguration();
			this.configurationService.save(c);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
