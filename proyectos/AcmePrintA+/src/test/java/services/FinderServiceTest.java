
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
	private CompanyService	companyService;


	//Requirement: 22.5 An actor who is authenticated as a company must be able to:
	//	Manage his or her finder, which involves updating the search criteria, listing its contents, and clearing it

	//In the case of negative tests, the business rule that is intended to be broken:
	//A designer is trying to modify the finder of a company

	// Analysis of sentence coverage total: Covered 37.9% 1119/314 total instructions
	// Analysis of sentence coverage saveFinder(): Covered 86.6% 116/134 total instructions

	// Analysis of data coverage: 70%

	//
	//only a rookie can edit his own finder
	@Test
	public void finderTestDriver() {

		final Object testingData[][] = {

			//TEST NEGATIVO:
			{
				"designer1", IllegalArgumentException.class
			}, {
				"customer3", IllegalArgumentException.class
			},

			//TEST POSITIVO:
			{
				"company1", null
			},

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

			final Finder f = this.companyService.findByPrincipal().getFinder();

			this.finderService.saveFinder(f);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
