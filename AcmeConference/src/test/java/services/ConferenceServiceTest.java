
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Conference;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConferenceServiceTest extends AbstractTest {

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private AdministratorService	adminService;


	@Test
	public void createSaveConferenceTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"admin", null
			},

			// TEST NEGATIVO: Crear una conferencia sin ser admin
			{
				"author1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.createSaveConferenceTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void createSaveConferenceTemplate(final String username, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		final Conference data = this.conferenceService.findOne(super.getEntityId("conference11"));

		try {
			super.authenticate(username);

			final Conference c = this.conferenceService.create();
			c.setAcronym("f");
			c.setAdministrator(this.adminService.findByPrincipal());
			c.setCameraReadyDeadline(data.getCameraReadyDeadline());
			c.setCategory(data.getCategory());
			c.setComments(data.getComments());
			c.setEndDate(data.getEndDate());
			c.setFee(data.getFee());
			c.setIsDraft(true);
			c.setNotificationDeadline(data.getNotificationDeadline());
			c.setStartDate(data.getStartDate());
			c.setSubmissionDeadline(data.getSubmissionDeadline());
			c.setSummary(data.getSummary());
			c.setTitle(data.getTitle());
			c.setVenue(data.getVenue());
			this.conferenceService.save(c);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
