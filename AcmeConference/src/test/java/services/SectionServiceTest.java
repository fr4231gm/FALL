package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Section;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SectionServiceTest extends AbstractTest {

	@Autowired
	private SectionService sectionService;

	@Test
	public void createSectionTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ super.getEntityId("tutorial1"), "administrator1", "Title", "Summary",
						"https://www.google.es", null },

				// TEST NEGATIVO: Un actor que no es administrador intenta
				// crearlo
				{ super.getEntityId("tutorial1"), "author1", "Title",
						"Summary", "https://www.google.es",
						IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.createSectionTemplate((int) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(Class<?>) testingData[i][5]);
			this.rollbackTransaction();
		}
	}

	protected void createSectionTemplate(int tutorialId, String username,
			String title, String summary, String pictures,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		Section section, toSave;

		try {

			super.authenticate(username);

			section = this.sectionService.create(tutorialId);
			section.setTitle(title);
			section.setSummary(summary);
			section.setPictures(pictures);

			toSave = this.sectionService.save(section, tutorialId);

			Assert.isTrue(toSave.getId() != 0);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void deleteSectionTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ super.getEntityId("section1"), "administrator1", null },

				// TEST NEGATIVO: Un actor que no es administrador intenta
				// borrarlo
				{ super.getEntityId("section1"), "author1",
						IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.deleteSectionTemplate((int) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void deleteSectionTemplate(int sectionId, String username,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		Section section;

		try {

			super.authenticate(username);

			section = this.sectionService.findOne(sectionId);
			this.sectionService.delete(section);

			this.sectionService.flush();
			
			Assert.isTrue(this.sectionService.findOne(sectionId) == null);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
