package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActivityServiceTest extends AbstractTest {

	@Autowired
	private ActivityService activityService;

	@Test
	public void listActivityTemplate() {

		final Object testingData[][] = {

		// TEST POSITIVO:
		{ super.getEntityId("conference1"), null },

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.listActivityTemplate((int) testingData[i][0],
					(Class<?>) testingData[i][1]);
			this.rollbackTransaction();
		}
	}

	protected void listActivityTemplate(int conferenceId,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			this.activityService.findActivitiesByConferenceId(conferenceId);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
