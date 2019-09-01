
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Comment;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CommentServiceTest extends AbstractTest {

	@Autowired
	private CommentService	commentService;


	//Requierement: 19.5 An actor who is authenticated:
	// Publish comments inside a published post
	//In the case of negative tests, the business rule that is intended to be broken:
	//No procede

	//Create comment

	// Analysis of sentence coverage total: Covered 21.0% 26/124 total instructions
	// Analysis of sentence coverage create(): Covered 100% 23/23 total instructions

	// Analysis of data coverage: 100%

	@Test
	public void createCommentTestDriver() {

		final Object testingData[][] = {

			// 1. TEST POSITIVO: 
			{
				"customer4", super.getEntityId("post1"), null
			},

			// 2. TEST POSITIVO
			{
				"customer1", super.getEntityId("post1"), null
			},

			// 3. TEST POSITIVO: 
			{
				"company1", super.getEntityId("post3"), null
			}

		//TEST NEGATIVO: NO PROCEDE

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.createCommentTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void createCommentTemplate(final String username, final int postId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			this.commentService.create(postId);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	//Save comment

	// Analysis of sentence coverage total: Covered 49.2% 61/124 total instructions
	// Analysis of sentence coverage create(): Covered 100% 23/23 total instructions
	// Analysis of sentence coverage save(): Covered 92.1% 35/38 total instructions

	// Analysis of data coverage: 100%

	@Test
	public void saveCommentTestDriver() {

		final Object testingData[][] = {

			// 1. TEST POSITIVO: 
			{
				"customer4", super.getEntityId("post1"), null
			},

			// 2. TEST POSITIVO
			{
				"customer1", super.getEntityId("post1"), null
			},

			// 3. TEST POSITIVO: 
			{
				"company1", super.getEntityId("post3"), null
			}

		//TEST NEGATIVO: NO PROCEDE

		};

		for (int i = 0; i < testingData.length; i++) {
			this.startTransaction();
			this.saveCommentTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void saveCommentTemplate(final String username, final int postId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			//Nos autenticamos como el usuario correspondiente
			super.authenticate(username);

			final Comment c = this.commentService.create(postId);
			c.setType("PRINTING EXPERIENCE");
			c.setDescription("x");
			c.setPictures("x");
			c.setScore(2.0);
			this.commentService.save(c);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
