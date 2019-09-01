
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
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TutorialServiceTest extends AbstractTest {

	// Service under test ------------------------------------------

	@Autowired
	private TutorialService	tutorialService;


	// Tests -------------------------------------------------------

	@Test
	public void testSaveTutorial() {
		final Tutorial tutorial, saved;
		final Collection<Tutorial> tutorials;
		super.authenticate("handyworker1");
		tutorial = this.tutorialService.create();

		//...Initialise the Tutorial...
		tutorial.setPictureUrl("http://pictureUrl.com");
		tutorial.setSummary("summary1");
		tutorial.setTitle("Titulo para tutorial");

		saved = this.tutorialService.save(tutorial);
		tutorials = this.tutorialService.findAll();
		Assert.isTrue(tutorials.contains(saved));
	}

	@Test
	public void testDeleteTutorial() {
		final Tutorial tutorial;

		tutorial = this.tutorialService.findAll().iterator().next();
		this.tutorialService.delete(tutorial);
		Assert.isTrue(!this.tutorialService.findAll().contains(tutorial));
	}

}
