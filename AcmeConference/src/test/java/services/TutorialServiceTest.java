package services;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Tutorial;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TutorialServiceTest extends AbstractTest {

	@Autowired
	private TutorialService tutorialService;

	@Test
	public void createTutorialTemplate() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ super.getEntityId("conference9"), "admin", "Title",
						"Speakers", "01/01/2020 12:00", 20, "Room 1",
						"Summary..", "https://www.google.es", null },

				// TEST NEGATIVO: Un actor que no es administrador intenta
				// crearlo
				{ super.getEntityId("conference9"), "author1", "Title",
						"Speakers", "01/01/2020 12:00", 20, "Room 1",
						"Summary..", "https://www.google.es",
						IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.createTutorialTemplate((int) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(int) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8],
					(Class<?>) testingData[i][9]);
			this.rollbackTransaction();
		}
	}

	protected void createTutorialTemplate(int conferenceId, String username,
			String title, String speakers, String startMomentString,
			int duration, String room, String summary, String attachments,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		Tutorial tutorial, toSave;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date startMoment = sdf.parse(startMomentString);
			super.authenticate(username);

			tutorial = this.tutorialService.create(conferenceId);
			tutorial.setTitle(title);
			tutorial.setSpeakers(speakers);
			tutorial.setStartMoment(startMoment);
			tutorial.setDuration(duration);
			tutorial.setRoom(room);
			tutorial.setSummary(summary);
			tutorial.setAttachments(attachments);

			toSave = this.tutorialService.save(tutorial);

			Assert.isTrue(toSave.getId() != 0);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void deleteTutorialTemplate() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ super.getEntityId("tutorial1"), "admin", null },

				// TEST NEGATIVO: Un actor que no es administrador intenta
				// borrarlo
				{ super.getEntityId("tutorial1"), "author1",
						IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.deleteTutorialTemplate((int) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void deleteTutorialTemplate(int tutorialId, String username,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		Tutorial tutorial;

		try {

			super.authenticate(username);

			tutorial = this.tutorialService.findOne(tutorialId);
			this.tutorialService.delete(tutorial);

			Assert.isTrue(this.tutorialService.findOne(tutorialId) == null);

			this.tutorialService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
	}
}
