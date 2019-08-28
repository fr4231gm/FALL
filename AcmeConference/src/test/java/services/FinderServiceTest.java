
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
	private AuthorService	authorService;


	//only a author can edit his own finder
	@Test
	public void finderTestDriver() {

		final Object testingData[][] = {

			//TEST POSITIVO:
			{
				"author1", null
			}, //Usar finder siendo author

			//TEST NEGATIVO:
			{
				"sponsor1", IllegalArgumentException.class
			}, //Usar finder siendo sponsor

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

			final Finder f = this.authorService.findByPrincipal().getFinder();

			this.finderService.saveFinder(f);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
