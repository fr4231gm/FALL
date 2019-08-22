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
import domain.Panel;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PanelServiceTest extends AbstractTest {

	@Autowired
	private PanelService panelService;

	@Test
	public void createPanelTemplate() {

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
			this.createPanelTemplate((int) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(int) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8],
					(Class<?>) testingData[i][9]);
			this.rollbackTransaction();
		}
	}

	protected void createPanelTemplate(int conferenceId, String username,
			String title, String speakers, String startMomentString,
			int duration, String room, String summary, String attachments,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		Panel panel, toSave;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date startMoment = sdf.parse(startMomentString);
			super.authenticate(username);

			panel = this.panelService.create(conferenceId);
			panel.setTitle(title);
			panel.setSpeakers(speakers);
			panel.setStartMoment(startMoment);
			panel.setDuration(duration);
			panel.setRoom(room);
			panel.setSummary(summary);
			panel.setAttachments(attachments);

			toSave = this.panelService.save(panel);

			Assert.isTrue(toSave.getId() != 0);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void deletePanelTemplate() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ super.getEntityId("panel1"), "admin", null },

				// TEST NEGATIVO: Un actor que no es administrador intenta
				// borrarlo
				{ super.getEntityId("panel1"), "author1",
						IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.deletePanelTemplate((int) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void deletePanelTemplate(int panelId, String username,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		Panel panel;

		try {

			super.authenticate(username);

			panel = this.panelService.findOne(panelId);
			this.panelService.delete(panel);

			Assert.isTrue(this.panelService.findOne(panelId) == null);

			this.panelService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
	}
}
