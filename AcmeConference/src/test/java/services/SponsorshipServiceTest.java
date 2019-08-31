
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

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
	
	@Test
	public void CreateEditSponsorshipTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:

			{
				"sponsor1", "http://www.blog.juliopari.com/wp-content/uploads/2011/04/frontbanner960_2.jpg", "http://www.opera.es", null
			},

			// TESTS NEGATIVOS:

			{
				"author1", "http://www.blog.juliopari.com/wp-content/uploads/2011/04/frontbanner960_2.jpg", "http://www.opera.es", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();

			this.CreateEditSponsorshipTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			this.rollbackTransaction();
		}
	}

	protected void CreateEditSponsorshipTemplate(final String username, final String banner, final String targetPage, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			super.authenticate(username);

			final Sponsorship sponsorship = this.sponsorshipService.create();

			sponsorship.setBanner(banner);
			sponsorship.setTargetUrl(targetPage);

			// UPDATING

			this.sponsorshipService.save(sponsorship);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void deleteSponsorshipTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:

			{
				"sponsor1", "sponsorship7", null
			},

			// TESTS NEGATIVOS:
			{
				"sponsor0", "sponsorship7", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.deleteSponsorshipTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void deleteSponsorshipTemplate(final String username, final String sponsorshipId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(username);
			this.sponsorshipService.delete(this.sponsorshipService.findOne(super.getEntityId(sponsorshipId)));
			Assert.isNull(this.sponsorshipService.findOne(super.getEntityId(sponsorshipId)));
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
