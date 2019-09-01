
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private CreditCardService	creditCardService;


	// Tests -------------------------------------------------------

	@Test
	public void testSaveSponsorship() {
		final Sponsorship sponsorship, saved;
		final Collection<Sponsorship> sponsorships;
		super.authenticate("sponsor1");
		sponsorship = this.sponsorshipService.create();
		final CreditCard c = new CreditCard();
		c.setBrandName("Sponsorship1");
		c.setCVV(101);
		c.setExpirationMonth(11);
		c.setExpirationYear(19);
		c.setHolderName("VISA");
		c.setNumber("5555555555554444");
		CreditCard savedcc = this.creditCardService.save(c);

		//...Initialise the Sponsorship...
		sponsorship.setBannerUrl("http://bannerUrl.com");
		sponsorship.setCreditCard(savedcc);
		sponsorship.setLink("http://link.com");

		saved = this.sponsorshipService.save(sponsorship);
		sponsorships = this.sponsorshipService.findAll();
		Assert.isTrue(sponsorships.contains(saved));
	}
}
