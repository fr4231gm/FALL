
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
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private SocialProfileService	socialProfileService;
	
	@Autowired
	private ActorService			actorService;
	

	// Tests -------------------------------------------------------

	@Test
	public void testSaveSocialProfile() {
		final SocialProfile socialProfile, saved;
		Collection<SocialProfile> categories;
		super.authenticate("admin");
		categories = this.socialProfileService.findAll();
		socialProfile = this.socialProfileService.create();
		//... Initialise the SocialProfile...
		socialProfile.setNick("testing SocialProfile");
		socialProfile.setLink("https://www.google.es");
		socialProfile.setSocialNetwork("TestNetwork");
		socialProfile.setActor(this.actorService.findByPrincipal());
		saved = this.socialProfileService.save(socialProfile);
		categories = this.socialProfileService.findAll();
		Assert.isTrue(categories.contains(saved));
	}
	
	@Test
	public void testDeleteSocialProfile() {
		final SocialProfile socialProfile, saved;
		Collection<SocialProfile> categories;
		super.authenticate("admin");
		categories = this.socialProfileService.findAll();
		socialProfile = this.socialProfileService.create();
		//... Initialise the SocialProfile...
		socialProfile.setNick("testing SocialProfile");
		socialProfile.setLink("https://www.google.es");
		socialProfile.setSocialNetwork("TestNetwork");
		socialProfile.setActor(this.actorService.findByPrincipal());
		saved = this.socialProfileService.save(socialProfile);
		categories = this.socialProfileService.findAll();
		Assert.isTrue(categories.contains(saved));
		this.socialProfileService.delete(saved);
		categories = this.socialProfileService.findAll();
		Assert.isTrue(!categories.contains(saved));

	}

}
