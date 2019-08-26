package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Submission;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SubmissionServiceTest extends AbstractTest {

	@Autowired
	private SubmissionService submissionService;

	@Test
	public void createSubmissionTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{ "author1", super.getEntityId("conference7"), null },

				// TEST NEGATIVO: Crear una submission para una conferencia cuyo
				// deadline ya ha pasado
				{ "author1", super.getEntityId("conference1"),
						IllegalArgumentException.class },

				// TEST NEGATIVO: Un actor que no es autor intenta crear una
				// submission
				{ "admin", super.getEntityId("conference7"),
						IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.createSubmissionTemplate((String) testingData[i][0],
					(int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void createSubmissionTemplate(String username, int conferenceId,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		Submission s, saved;

		try {
			super.authenticate(username);

			s = this.submissionService.create(conferenceId);
			saved = this.submissionService.save(s);

			Assert.isTrue(saved.getId() != 0);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	/*@Test
	public void assignSubmissionTestDriver() {

		final Object testingData[][] = {

				// TEST POSITIVO:
				{  },

				// TEST NEGATIVO: Crear una submission para una conferencia cuyo
				// deadline ya ha pasado
				{  },

				// TEST NEGATIVO: Un actor que no es autor intenta crear una
				// submission
				{  } 
				
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.assignSubmissionTemplate((String) testingData[i][0],
					(int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void assignSubmissionTemplate(String username, int submissionId,
			final Class<?> expected) {

		Class<?> caught;

		caught = null;

		Submission s, saved;

		try {
			super.authenticate(username);

			s = this.submissionService.findOne(submissionId);
			saved = this.submissionService.saveAssign(s);

			Assert.isTrue(saved.getId() != 0);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}*/

}
