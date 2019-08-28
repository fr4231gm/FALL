
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Reviewer;
import domain.Submission;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SubmissionServiceTest extends AbstractTest {

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private ReviewerService		reviewerService;


	@Test
	public void createSubmissionTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"author1", super.getEntityId("conference7"), null
			},

			// TEST NEGATIVO: Crear una submission para una conferencia cuyo
			// deadline ya ha pasado
			{
				"author1", super.getEntityId("conference1"), IllegalArgumentException.class
			},

			// TEST NEGATIVO: Un actor que no es autor intenta crear una
			// submission
			{
				"admin", super.getEntityId("conference7"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.createSubmissionTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void createSubmissionTemplate(final String username, final int conferenceId, final Class<?> expected) {

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

	@Test
	public void assignSubmissionTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"admin", super.getEntityId("submission12"), super.getEntityId("reviewer1"), null
			},

			// TEST NEGATIVO: Un actor que no es autor intenta crear una
			// submission
			{
				"author1", super.getEntityId("submission12"), super.getEntityId("reviewer1"), IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.assignSubmissionTemplate((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
			this.rollbackTransaction();
		}
	}

	protected void assignSubmissionTemplate(final String username, final int submissionId, final int reviewerId, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		Submission s;

		try {
			final Collection<Reviewer> reviewers = new ArrayList<>();
			reviewers.add(this.reviewerService.findOne(reviewerId));
			super.authenticate(username);

			s = this.submissionService.findOne(submissionId);
			this.submissionService.setRev(s, reviewers);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void saveSubmissionTestDriver() {

		final Object testingData[][] = {

			// TEST POSITIVO:
			{
				"author1", super.getEntityId("submission1"), null
			},

			// TEST NEGATIVO: Un actor que no es autor intenta guardar una
			// submission
			{
				"author1", super.getEntityId("submission12"), IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.saveSubmissionTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void saveSubmissionTemplate(final String username, final int submissionId, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		Submission s, saved;

		try {
			super.authenticate(username);

			s = this.submissionService.findOne(submissionId);
			saved = this.submissionService.save(s);

			Assert.isTrue(saved.getId() != 0);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
