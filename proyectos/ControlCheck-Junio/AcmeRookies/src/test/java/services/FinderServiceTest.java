
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Finder;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	private FinderService	finderService;

	@Autowired
	private RookieService	rookieService;


	//Requierement: 17.2 An actor who is authenticated as a rookie must be able to:
	//	Manage his or her finder, which involves updating the search criteria, listing its contents, and clearing it

	//In the case of negative tests, the business rule that is intended to be broken:
	//A company is trying to modify the finder of a rookie

	
	// Analysis of sentence coverage total: Covered 56.9% 123/216 total instructions
	// Analysis of sentence coverage saveFinder(): Covered 99.2% 120/121 total instructions
		
	// Analysis of data coverage: 100%
	
	//
	//only a rookie can edit his own finder
	@Test
	public void finderTestDriver() {

		final Object testingData[][] = {

			//TEST POSITIVO:
			{
				"rookie1", null
			}, //Editar configuracion siendo admin

			//TEST NEGATIVO:
			{
				"company1", IllegalArgumentException.class
			}, //Editar configuracion siendo brotherhood

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.finderTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	//Templates---------------------------------------------------------------------------------

	protected void finderTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			final Finder f = this.rookieService.findByPrincipal().getFinder();

			this.finderService.saveFinder(f);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
