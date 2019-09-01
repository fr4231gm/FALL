
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
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private SponsorService		sponsorService;

	// Tests -------------------------------------------------------

	@Test
	public void testSaveSponsor() {
		final Sponsor sponsor, saved;
		final Collection<Sponsor> sponsors;
		sponsor = this.sponsorService.create();
		sponsor.setAddress("Pepito Grillo nº 20");
		sponsor.setEmail("micorreo@gmail.com");
		sponsor.setIsSuspicious(false);
		sponsor.setMiddleName("middleNameSponsor1");
		sponsor.setName("sponsor1");
		sponsor.setPhoneNumber("654654654");
		sponsor.setPhoto("https://www.google.es");
		sponsor.setSurname("SurnameSponsor1");
		sponsor.getUserAccount().setUsername("sponsor111");
		sponsor.getUserAccount().setPassword("sponsor111");
		saved = this.sponsorService.save(sponsor);
		sponsors = this.sponsorService.findAll();
		Assert.isTrue(sponsors.contains(saved));
	}

}
