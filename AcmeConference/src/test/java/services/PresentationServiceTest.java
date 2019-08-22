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
import domain.Paper;
import domain.Presentation;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PresentationServiceTest extends AbstractTest {

	@Autowired
	private PresentationService presentationService;

	@Test
	public void createPresentationTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ super.getEntityId("conference9"), "admin", "Title",
						"Speakers", "01/01/2020 12:00", 20, "Room 1",
						"Summary..", "https://www.google.es", "Title", "Summary", "https://www.google.es", false, null },

				// TEST NEGATIVO: Un actor que no es administrador intenta
				// crearlo
				{ super.getEntityId("conference9"), "author1", "Title",
						"Speakers", "01/01/2020 12:00", 20, "Room 1",
						"Summary..", "https://www.google.es", "Title", "Summary", "https://www.google.es", false,
						IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.createPresentationTemplate((int) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(int) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8],
					(String) testingData[i][9], (String) testingData[i][10],
					(String) testingData[i][11], (boolean) testingData[i][12],
					(Class<?>) testingData[i][13]);
			this.rollbackTransaction();
		}
	}

	protected void createPresentationTemplate(int conferenceId,
			String username, String title, String speakers,
			String startMomentString, int duration, String room,
			String summary, String attachments, String titlePaper,
			String summaryPaper, String document, boolean cameraReadyPaper,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		Presentation presentation, toSave;
		Paper paper;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date startMoment = sdf.parse(startMomentString);

			super.authenticate(username);

			paper = new Paper();
			paper.setTitle(titlePaper);
			paper.setSummary(summaryPaper);
			paper.setDocument(document);
			paper.setCameraReadyPaper(cameraReadyPaper);

			presentation = this.presentationService.create(conferenceId);
			presentation.setTitle(title);
			presentation.setSpeakers(speakers);
			presentation.setStartMoment(startMoment);
			presentation.setDuration(duration);
			presentation.setRoom(room);
			presentation.setSummary(summary);
			presentation.setAttachments(attachments);
			presentation.setPaper(paper);

			toSave = this.presentationService.save(presentation);

			Assert.isTrue(toSave.getId() != 0);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void deletePresentationTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ super.getEntityId("presentation1"), "admin", null },

				// TEST NEGATIVO: Un actor que no es administrador intenta
				// borrarlo
				{ super.getEntityId("presentation1"), "author1",
						IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.deletePresentationTemplate((int) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void deletePresentationTemplate(int presentationId,
			String username, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		Presentation presentation;

		try {

			super.authenticate(username);

			presentation = this.presentationService.findOne(presentationId);
			this.presentationService.delete(presentation);

			Assert.isTrue(this.presentationService.findOne(presentationId) == null);

			this.presentationService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
